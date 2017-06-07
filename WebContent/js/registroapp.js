/**
 * Created by pablomaciasmu on 13/5/17.
 */

angular.module('registroApp', ["ngRoute"]).factory("usersFactory", function ($http) {
    var url = 'https://localhost:8443/exchange/rest/users/';
    var interfaz = {
        addUser: function (user) {
            return $http.post(url, user)
                .then(function (response) {
                    return response.data;
                });
        }
    };
    return interfaz;

}).factory("usersLanguagesFactory", function ($http) {
    var url = 'https://localhost:8443/exchange/rest/userlanguage';
    var interfaz = {
        addUserLangs: function (userlang) {
            return $http.post(url, userlang)
                .then(function (response) {
                    return response.status;
                });
        }
    };
    return interfaz;

}).factory("languagesFactory", function ($http) {
    var url = 'https://localhost:8443/exchange/rest/language';
    var interfaz = {
        leerLanguages: function () {
            return $http.get(url)
                .then(function (response) {
                    return response.data;
                });
        }
    };
    return interfaz;

}).controller("registroCtrl", function (languagesFactory, usersLanguagesFactory, usersFactory, $route, $templateCache, $location, $window) {
    var vm = this;
    vm.user = {};
    vm.user.imagen = "1";
    vm.userLangs = [];
    vm.languages = [];
    vm.nombres = [];
    vm.userL = {};
    vm.funciones = {
        obtenerIdiomas: function () {
            languagesFactory.leerLanguages()
                .then(function (respuesta) {
                    vm.languages = respuesta;
                    console.log("Trayendo lenguajes, Respuesta: ", respuesta);
                }, function (respuesta) {
                    console.log("Error:", respuesta.status);
                })
        },
        anadirUsuario: function () {
            usersFactory.addUser(vm.user)
                .then(function (respuesta) {
                    console.log("OK USER: ", respuesta);
                    angular.forEach(vm.userLangs, function (userlang) {
                        userlang.idu = respuesta;
                    });
                    console.log("FINAL:", vm.userLangs);
                    usersLanguagesFactory.addUserLangs(vm.userLangs)
                        .then(function (respuesta) {
                            console.log("OK: ", respuesta);
                            $window.location.href = 'login';
                        });
                }, function (respuesta) {
                    console.log("UL:", vm.userLangs);
                    console.log("ULV:", vm.n_languages);
                    console.log("Error: inserción de usuario no realizado: ", respuesta);
                })
        },
        idioma_elegido: function (language) {
            var i = 0;
            if (angular.isUndefined(language))
                return false;
            angular.forEach(vm.userLangs, function (userlang) {
                if (angular.equals(language.idl, userlang.idl)) {
                    i++;
                }
            });
            if (i > 0)
                return false;
            return true;
        },
        add_idioma: function (idioma) {
            if (idioma == null)
                return;
            vm.userL.idl = idioma.idl;
            vm.nombres.push(idioma.langname);
            vm.userLangs.push(angular.copy(vm.userL));
            //vm.userLangs.push(idioma);
            console.log("ULV:", vm.userLangs);
            //console.log("ULV:", vm.userLangs);
        }
    };
    console.log("PRUEBAA");
    vm.funciones.obtenerIdiomas();
    console.log("ULV:", vm.n_languages);
});