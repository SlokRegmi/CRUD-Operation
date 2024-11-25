Ext.define('MyApp.controller.LoginController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.login',

    onLoginClick: function(button) {
        var form = button.up('form').getForm();
        var loginWindow = button.up('window');
        var loginType = loginWindow.loginType;

        if (form.isValid()) {
            var values = form.getValues();

            Ext.Ajax.request({
                url: 'http://localhost:8080/auth/login',
                method: 'POST',
                jsonData: values,
                headers: {
                    'X-Referer-Type': loginType,
                },
                success: function(response) {
                    var result = Ext.decode(response.responseText);
                    if (result.message && result.message.includes('successful')) {
                        // Store user data in localStorage
                        localStorage.setItem('isLoggedIn', 'true');
                        localStorage.setItem('userType', loginType);
                        localStorage.setItem('userData', Ext.encode(result));

                        // Close login window
                        loginWindow.close();

                        // Get viewport and update content
                        var viewport = Ext.ComponentQuery.query('viewport')[0];
                        if (viewport) {
                            viewport.removeAll(true);
                            viewport.add({
                                xtype: 'home'
                            });
                        }
                    } else {
                        Ext.Msg.alert('Login Failed', result.error || 'Authentication failed');
                    }
                },
                failure: function(response) {
                    Ext.Msg.alert('Error', 'Server error occurred during login');
                }
            });
        } else {
            Ext.Msg.alert('Validation Error', 'Please fill in all required fields.');
        }
    },

    onSignupClick: function(button) {
        var loginWindow = button.up('window');
        var loginType = loginWindow.loginType;
        loginWindow.close();

        Ext.create('MyApp.view.Signup', {
            loginType: loginType
        });
    }
});