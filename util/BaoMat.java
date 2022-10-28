/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author thaond
 */
public class BaoMat {
    public String encryptPass(String password) throws NoSuchAlgorithmException {

        String passEncrypt;

        MessageDigest md5 = null;
        md5 = MessageDigest.getInstance("MD5");
        md5.update(password.getBytes());
        BigInteger dis = new BigInteger(1, md5.digest());
        passEncrypt = dis.toString();
        
        return passEncrypt;

    }
}
