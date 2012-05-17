/*
 * File: app/controller/MyController.js
 *
 * This file was generated by Sencha Designer version 2.0.0.
 * http://www.sencha.com/products/designer/
 *
 * This file requires use of the Sencha Touch 2.0.x library, under independent license.
 * License of Sencha Designer does not include license for Sencha Touch 2.0.x. For more
 * details see http://www.sencha.com/license or contact license@sencha.com.
 *
 * You should implement event handling and custom methods in this
 * class.
 */

Ext.define('MyApp.controller.MyController', {
    extend: 'Ext.app.Controller',

    config: {
        refs: {
            asd: '#mybutton',
            mycontainer: '#mycontainer',
            titlecontainer: '#titlecontainer',
            mylist: '#mylist'
        },

        control: {
            "asd": {
                tap: 'onMybuttonTap'
            },
            "mycontainer": {
                painted: 'onMycontainerPainted'
            },
            "titlecontainer": {
                activate: 'onContainerActivate1'
            }
        }
    },

    onMycontainerPainted: function(component, options) {
        console.log("painted");
        //this.refreshLocation();


    },

    onMybuttonTap: function(button, e, options) {
        this.refreshLocation();
    },

    fetchDepartures: function(position) {
        var lat = (""+position.coords.latitude).replace(".","").substring(0,8);
        var lon = (""+position.coords.longitude).replace(".","").substring(0,8);

        var store_ = Ext.data.StoreManager.lookup('MyJsonStore');
        var url = 'bvg/index?lat='+lat+'&lon='+lon;
        //console.log(store_.getProxy().url);
        store_.getProxy().setUrl(url);


        store_.load({
            callback: function(records, operation, success) {
                console.log('loaded');
                var mainCont = Ext.ComponentManager.get('mycontainer');
                //console.log(mainCont);

                if(!mainCont.isPainted()){
                    console.log('mycontainer is not visible');
                    mainCont.show();
                }

            },
            scope: this
        });







    },

    locationNotFound: function() {
        alert("Oh noes! We couldn't find you");
    },

    refreshLocation: function() {
        console.log('refreshLocation');
        if (navigator.geolocation) {

            var options = {
                timeout: 10000,
                maximumAge: 20000,
                enableHighAccuracy: true
            };

            navigator.geolocation.getCurrentPosition(this.fetchDepartures, this.locationNotFound, options);

            var task = new Ext.util.DelayedTask(function(){
                this.updateCounters();
            },this);
            task.delay(62000);    

        } else {
            console.log("Geolocation is missing");
        }

    },

    onContainerActivate1: function(container, newActiveItem, oldActiveItem, options) {
        console.log('title activatee');
        //var cont = Ext.create('MyApp.view.MyContainer', {fullscreen: true});
        //this.getMycontainer().show();
        var cont = Ext.create('MyApp.view.MyContainer', {fullscreen: true});
        //cont.show();

        this.refreshLocation();

    },

    updateCounters: function() {
        console.log('updating counters');
        var store_ = Ext.data.StoreManager.lookup('MyJsonStore');
        store_.each(function(record){
            var length = record.data.departures.length;
            for(i=0;i<length;i++){
                if(record.data.departures[i].predictedTime!==null)
                record.data.departures[i].predictedTime-=1;
                if(record.data.departures[i].plannedTime!==null)
                record.data.departures[i].plannedTime-=1;
            }

        });

        this.getMylist().refresh();
    }

});