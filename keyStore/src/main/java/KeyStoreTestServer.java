import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * Created by mi on 16-12-29.
 */
public class KeyStoreTestServer {

    public static void main(String[] args)
            throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException,
            UnrecoverableKeyException, KeyManagementException {
        String key = "/home/mi/test.keystore";
        //keyStore的类型　默认是jdk
        KeyStore keyStore = KeyStore.getInstance("JKS");
        //创建JKS密钥访问库,123456是keyStore的密码
        keyStore.load(new FileInputStream(key), "123456".toCharArray());

        //123456是key的密码
        //创建管理JKS密钥库的x509密钥管理器,用来管理密钥,需要key的密码
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, "123456".toCharArray());

        //构造SSL环境,指定版本为3.0,也可以使用TLSv1,但是SSLv3更加常用
        SSLContext sslContext = SSLContext.getInstance("SSLv3");

        //第二个参数TrustManager[]是认证管理器,在需要双向认证时使用, 构造ssl环境
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

        SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
        SSLServerSocket serverSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(9999);
        //创建serversocket,监听,并传输数据来验证授权
        for (int i = 0; i < 15; i++) {
            final Socket socket = serverSocket.accept();
            new Thread() {
                @Override
                public void run() {
                    InputStream inputStream = null;
                    OutputStream outputStream = null;
                    try {
                        inputStream = socket.getInputStream();
                        outputStream = socket.getOutputStream();

                        byte[] buf = new byte[1024];
                        int len = inputStream.read(buf);
                        System.out.println(new String(buf));
                        outputStream.write("ssl test".getBytes());
                    } catch (IOException e) {

                    } finally {
                        try {
                            inputStream.close();
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
        serverSocket.close();
    }
}
