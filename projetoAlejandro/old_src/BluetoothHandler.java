import javax.bluetooth.*;
import java.rmi.Remote;
import java.util.ArrayList;

public class BluetoothHandler {
    private static Object lock = new Object();
    private ArrayList<RemoteDevice> btDevices = new ArrayList<RemoteDevice>();
    DiscoveryListener discoveryListener = new DiscoveryListener() {
        @Override
        public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) {
            btDevices.add(remoteDevice);
        }

        @Override
        public void servicesDiscovered(int arg0, ServiceRecord[] services) {
            System.out.println("Tamanho do Services: "+services.length);
            for (int i = 0; i < services.length; i++) {
                String url = services[i].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
                System.out.println("for rodando.");
                if (url == null) {
                    System.out.println("URL NULA!");
                    continue;
                }
                System.out.println("url não é nula");

                DataElement serviceName = services[i].getAttributeValue(0x0100);
                if (serviceName != null) {
                    System.out.println("service " + serviceName.getValue() + " found " + url);
                } else {
                    System.out.println("service found " + url);
                }

                if (serviceName.getValue().equals("OBEX Object Push ")) {
                    //sendMessageToDevice(url);
                }
            }

        }

        @Override
        public void serviceSearchCompleted(int arg0, int arg1) {
            synchronized (lock) {
                lock.notify();
            }
        }

        @Override
        public void inquiryCompleted(int i) {
            synchronized (lock) {
                lock.notify();
            }
        }
    };
    LocalDevice localDevice;
    DiscoveryAgent agent;

    public BluetoothHandler() {
        try {
            localDevice = LocalDevice.getLocalDevice();
            agent = localDevice.getDiscoveryAgent();
        } catch (BluetoothStateException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<RemoteDevice> ScanDevices() {
        try {
            agent.startInquiry(DiscoveryAgent.GIAC, discoveryListener);

            try {
                synchronized (lock) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Device Inquiry Completed. ");

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Dispositivo utilizado: "+btDevices.get(0));
        SearchService(btDevices.get(0));
        return btDevices;
    }

    public void SearchService(RemoteDevice device) {
        UUID[] uuidSet = new UUID[1];
        uuidSet[0] = new UUID(0x1105); //OBEX Object Push service

        int[] attrIDs = new int[]{
                0x0100 // Service name
        };

        try {
            agent.searchServices(null, uuidSet, device, discoveryListener);
        } catch (BluetoothStateException e) {
            e.printStackTrace();
        }


        try {
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
    }

}