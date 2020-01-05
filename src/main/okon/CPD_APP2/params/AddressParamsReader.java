package okon.CPD_APP2.params;

import okon.CPD_APP2.AppException;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class AddressParamsReader {
    public static Properties readProperties(File file) {
        Properties result = new Properties();
        try {
            result.load(new FileInputStream(file));
        } catch (Exception e) {
            throw new AppException(e);
        }
        return result;
    }
}
