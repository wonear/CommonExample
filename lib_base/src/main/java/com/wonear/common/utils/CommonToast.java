package com.wonear.common.utils;

import es.dmoral.toasty.Toasty;

public class CommonToast {


    public static void init() {
        Toasty.Config.getInstance()
                .tintIcon(false) // optional (apply textColor also to the icon)
                .allowQueue(false) // optional (prevents several Toastys from queuing)
                .apply(); // required
    }

    public static void toast(String message) {
        Toasty.normal(ContextManager.getContext(), message).show();
    }

//    public static void toastError(String message) {
//        Toasty.error(ContextManager.getContext(), message, Toast.LENGTH_SHORT, true).show();
//    }
//    public static void toastSuccess(String message) {
//        Toasty.success( ContextManager.getContext(), message, Toast.LENGTH_SHORT, true).show();
//    }


}
