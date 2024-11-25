Ext.define('MyApp.Util', {
    singleton: true,

    getQueryParam: function(param) {
        var params = Ext.urlDecode(window.location.search.substring(1));
        return params[param] || null;
    }
});
