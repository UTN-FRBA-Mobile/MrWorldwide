package mobile.frba.utn.tpmobile.singletons

class AppHolder {
    companion object {
        private val APP: android.app.Application

        init {
            try {
                val c = Class.forName("android.app.ActivityThread")
                APP = c.getDeclaredMethod("currentApplication").invoke(null) as android.app.Application
            } catch (ex: Throwable) {
                throw AssertionError(ex)
            }
        }
        fun getContext(): android.content.Context{
            return APP.applicationContext
        }
        fun getApp(): android.app.Application {
            return APP
        }
    }
}