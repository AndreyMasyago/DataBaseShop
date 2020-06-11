package DataBase.Request;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryRequest {
    public Date arrivingDateOnStorage;

    public List<DeliveryContentRequest> content;

    public Map<String, Object> validate() {
        Map<String, Object> errors = new HashMap<>();
        boolean returnErrors = false;

        Map<Integer, Object> contentErrors = new HashMap<>();

        for (int i = 0; i < content.size(); ++i) {
            Map<String, String> contentErr = new HashMap<>();
            boolean hasErrors = false;

            if (content.get(i).amount < 0) {
                hasErrors = true;
                contentErr.put("amount", "Количество должно быть > 0");
            }

            if (hasErrors) {
                contentErrors.put(i, contentErr);
                returnErrors = true;
            }
        }

        errors.put("content", contentErrors);

        if (returnErrors) {
            return errors;
        }

        return null;
    }
}
