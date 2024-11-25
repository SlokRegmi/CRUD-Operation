Ext.define('MyApp.view.Login', {
    extend: 'Ext.window.Window',
    xtype: 'login',

    controller: 'login',

    title: 'Login',
    closable: false,
    autoShow: true,
    modal: true,
    layout: 'fit',

    config: {
        loginType: null // Accept 'admin' or 'user'
    },

    items: {
        xtype: 'form',
        reference: 'form',
        bodyPadding: 10,
        defaultType: 'textfield',

        items: [
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
            }
        ],

        buttons: [
            {
                text: 'Login',
                formBind: true,
                listeners: {
                    click: 'onLoginClick'
                }
            },
            {
                text: 'Signup',
                listeners: {
                    click: 'onSignupClick'
                }
            }
        ]
    }
});
