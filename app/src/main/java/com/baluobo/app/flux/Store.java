package com.baluobo.app.flux;


import com.squareup.otto.Bus;

/**
 * Flux的Store模块
 * Created by:chenliliang
 * Created on:16/5/10.
 */
public abstract class Store {
    private static final Bus bus = new Bus();

    protected Store() {
    }

    public void register(final Object view) {
        try {
            bus.register(view);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void unregister(final Object view) {
        try {
            bus.unregister(view);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void emitStoreChange(String type){
        bus.post(changeEvent(type));
    }

    public void emitStoreChange(String type, String msg){
        bus.post(changeEvent(type, msg));
    }

    public abstract void onAction(Action action);

    public abstract StoreChangeEvent changeEvent(String type);

    public abstract StoreChangeEvent changeEvent(String type, String msg);

    public class StoreChangeEvent {
        private String type;
        private String message;
        public StoreChangeEvent(String type){
            this.type = type;
        }

        public StoreChangeEvent(String type, String msg){
            this.type = type;
            this.message = msg;
        }

        public String getType() {
            return type;
        }

        public String getMessage(){
            return message;
        }

    }
}
