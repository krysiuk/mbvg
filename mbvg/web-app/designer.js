/*
 * File: designer.js
 *
 * This file was generated by Sencha Designer version 2.0.0.
 * http://www.sencha.com/products/designer/
 *
 * This file requires use of the Sencha Touch 2.0.x library, under independent license.
 * License of Sencha Designer does not include license for Sencha Touch 2.0.x. For more
 * details see http://www.sencha.com/license or contact license@sencha.com.
 *
 * This file will be auto-generated each and everytime you save your project.
 *
 * Do NOT hand edit this file.
 */

Ext.Loader.setConfig({
    enabled: true
});

Ext.application({
    models: [
        'Deps',
        'Departure',
        'MyModel2'
    ],

    stores: [
        'MyJsonStore',
        'MyStore'
    ],

    views: [
        'TitleContainer',
        'MyContainer'
    ],

    name: 'MyApp',

    phoneIcon: 'data/HaltestelleIcon.png',

    phoneStartupScreen: 'data/Haltestelle2.png',

    tabletIcon: 'data/HaltestelleIcon.png',

    controllers: [
        'MyController'
    ],

    launch: function() {
        console.log('launch');
        Ext.create('MyApp.view.TitleContainer', {fullscreen: true});
    }
});
