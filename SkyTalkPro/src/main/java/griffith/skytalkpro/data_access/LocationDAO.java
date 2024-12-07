package griffith.skytalkpro.data_access;

import java.util.Map;

public interface LocationDAO {
    Map<String, String> getLocationInfo(String ipAddress);
    String getCurrentIPAddress();
}
