Ext.define('MyApp.view.Home', {
    extend: 'Ext.container.Container',
    xtype: 'home',

    requires: [
        'Ext.layout.container.VBox'
    ],

    layout: {
        type: 'vbox',
        align: 'center',
        pack: 'center'
    },

    initComponent: function() {
        var me = this;
        var isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
        var userData = Ext.decode(localStorage.getItem('userData') || '{}');

        if (!isLoggedIn) {
            Ext.create('MyApp.view.Login', {
                loginType: localStorage.getItem('userType') || 'user'
            });
            return;
        }

        // Add logout button and welcome message
        me.items = [{
            xtype: 'panel',
            title: 'Home',
            width: 400,
            items: [{
                xtype: 'container',
                padding: 20,
                html: '<h2>Welcome, ' + (userData.name || userData.username ) + 'User'+ '!</h2>'
            }],
            bbar: [{
                xtype: 'button',
                text: 'Logout',
                handler: function() {
                    // Clear local storage
                    localStorage.clear();
                    
                    // Get viewport and show login
                    var viewport = Ext.ComponentQuery.query('viewport')[0];
                    if (viewport) {
                        viewport.removeAll(true);
                        Ext.create('MyApp.view.Login', {
                            loginType: 'user'
                        });
                    }
                }
            }]
        }];

        me.callParent(arguments);
    }
});