    Ext.define('MyApp.view.main.Main', {
        extend: 'Ext.container.Viewport', // This makes it the main container
        xtype: 'viewport', // This is the identifier you use in your code

        layout: 'fit', // Ensures child components take up the full space
        items: [
            {
                xtype: 'panel', // Placeholder for initial content
                title: 'Welcome',
                html: '<h1>Welcome to My Application</h1>',
            }
        ]
    });
