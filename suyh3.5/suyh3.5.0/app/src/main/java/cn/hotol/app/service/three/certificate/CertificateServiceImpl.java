package cn.hotol.app.service.three.certificate;


import org.springframework.stereotype.Service;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/*
 *      User: Wind
 *      Date: 2014/10/27
 *      Time: 9:50
 */
@Service
public class CertificateServiceImpl implements CertificateService, X509TrustManager {


    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

}
