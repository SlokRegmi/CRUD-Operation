Ext.define('MyApp.Application', {
    extend: 'Ext.app.Application',
    name: 'MyApp',

    requires: [
        'MyApp.view.Login',
        'MyApp.controller.LoginController',
        'MyApp.controller.SignupController',
        'MyApp.Util' // Include the Util class
    ],

    launch: function () {
        // Get the loginType from query parameters
        var loginType = MyApp.Util.getQueryParam('type') || 'user';

        // Display the login window with the specified loginType
        Ext.create('MyApp.view.Login', {
            loginType: loginType
        });
    }
});
