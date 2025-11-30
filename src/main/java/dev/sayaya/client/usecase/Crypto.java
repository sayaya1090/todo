package dev.sayaya.client.usecase;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "crypto")
class Crypto {
    public static native String randomUUID();
}
