Ext.define('MyApp.view.Signup', {
    extend: 'Ext.window.Window',
    xtype: 'signup',
    cls: 'full-size-window',

    requires: [
        'Ext.form.Panel'
    ],

    controller: 'signup',

    title: 'Signup',
    closable: false,
    autoShow: true,
    modal: true,
    layout: 'fit',

    initComponent: function() {
        var me = this;

        // Redirect to home if already logged in
        if (MyApp.isLoggedIn) { // Example state variable
            Ext.create('MyApp.view.Home');
            me.close();
            return;
        }

        me.callParent(arguments);
    },

    items: {
        xtype: 'form',
        reference: 'form',
        bodyPadding: 10,
        defaultType: 'textfield',

        items: [
            {
                name: 'firstName',
                fieldLabel: 'First Name',
                allowBlank: false
            },
            {
                name: 'lastName',
                fieldLabel: 'Last Name',
                allowBlank: false
            },
            {
                name: 'email',
                fieldLabel: 'Email',
                allowBlank: false
            },
            {
                name: 'password',
                fieldLabel: 'Password',
                inputType: 'password',
                allowBlank: false
            },
            {
                name: 'confirmPassword',
                fieldLabel: 'Confirm Password',
                inputType: 'password',
                allowBlank: false
            }
        ],

        buttons: [
            {
                text: 'Signup',
                formBind: true,
                listeners: {
                    click: 'onSignupSubmitClick'
                }
            },
            {
                text: 'Cancel',
                listeners: {
                    click: 'onCancelClick'
                }
            }
        ]
    }
});
