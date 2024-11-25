Ext.define('MyApp.controller.SignupController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.signup',

    onSignupSubmitClick: function(button) {
        var form = button.up('form').getForm();
        var signupWindow = button.up('window');
        var loginType = signupWindow.loginType;

        if (form.isValid()) {
            var values = form.getValues();

            if (values.password !== values.confirmPassword) {
                Ext.Msg.alert('Error', 'Passwords do not match');
                return;
            }

            Ext.Ajax.request({
                url: 'http://localhost:8080/auth/register',
                method: 'POST',
                jsonData: values,
                headers: {
                    'X-Referer-Type': loginType,
                },
                success: function(response) {
                    var result = Ext.decode(response.responseText);
                    if (result.message && result.message.includes('successful')) {
                        Ext.Msg.show({
                            title: 'Success',
                            message: 'Registration successful! Please login with your credentials.',
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.INFO,
                            fn: function() {
                                // Close signup window
                                signupWindow.close();
                                
                                // Create new login window
                                Ext.create('MyApp.view.Login', {
                                    loginType: loginType
                                });
                            }
                        });
                    } else {
                        Ext.Msg.alert('Registration Failed', result.error || 'Registration failed');
                    }
                },
                failure: function(response) {
                    Ext.Msg.alert('Error', 'Server error occurred during registration');
                }
            });
        } else {
            Ext.Msg.alert('Validation Error', 'Please fill in all required fields.');
        }
    },

    onCancelClick: function(button) {
        var signupWindow = button.up('window');
        var loginType = signupWindow.loginType;
        signupWindow.close();

        Ext.create('MyApp.view.Login', {
            loginType: loginType
        });
    }
});