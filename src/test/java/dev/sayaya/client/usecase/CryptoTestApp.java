package dev.sayaya.client.usecase;

import com.google.gwt.core.client.EntryPoint;

import static elemental2.dom.DomGlobal.console;

public class CryptoTestApp implements EntryPoint {
    @Override
    public void onModuleLoad() {
        try {
            String uuid = Crypto.randomUUID();
            console.log("UUID Generated: " + uuid);

            if (uuid != null && uuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"))
                console.log("UUID Format Valid");
            else console.error("UUID Format Invalid: " + uuid);

            String uuid2 = Crypto.randomUUID();
            if (!uuid.equals(uuid2)) console.log("UUID Uniqueness Valid");
            else console.error("UUID Collision Detected");

        } catch (Exception e) {
            console.error("Crypto Test Error: " + e.getMessage());
        }
    }
}