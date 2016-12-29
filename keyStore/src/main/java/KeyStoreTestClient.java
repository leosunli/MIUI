import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * Created by mi on 16-12-29.
 */
public class KeyStoreTestClient {
    public static void main(String[] args)
            throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException,
            UnrecoverableKeyException, KeyManagementException {
        String key = "/home/mi/test.keystore";

        //创建一个keyStore来管理密钥库
        KeyStore keyStore = KeyStore.getInstance("JKS");
        //创建JKS密钥访问库,123456是keyStore的密码
        keyStore.load(new FileInputStream(key), "123456".toCharArray());

        //创建TrustManagerFactory,管理授权证书
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
        trustManagerFactory.init(keyStore); //验证数据,可以不传入key密码

        //构造SSL环境,指定版本为3.0,也可以使用TLSv1,但是SSLv3更加常用
        SSLContext sslContext = SSLContext.getInstance("SSLv3");

        //KeyManager[] 第一个参数是授权的密钥管理器,用来授权验证.
        //第二个是被授权的证书管理器 用来验证服务器端的证书, 只验证服务器数据, 第一个管理器可以为null
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        //创建serversocket通过传输数据来验证授权
        SSLSocket socket = (SSLSocket) sslSocketFactory.createSocket("127.0.0.1", 9999);

        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("client".getBytes());
        byte[] buf = new byte[1024];
        int len = inputStream.read(buf);
        System.out.println(new String(buf));
        outputStream.close();
        inputStream.close();
    }
}
