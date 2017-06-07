/**
 * Created by pablomaciasmu on 13/5/17.
 */

angular.module('lanexApp', ["ngRoute", "ngPagination"]).config(function ($routeProvider) {
    $routeProvider.when("/", {
        controller: "PanelCtrl",
        controllerAs: "vm",
        templateUrl: "panelusuario.html",
        resolve: {
            // provoca 100 milisegundos (0,1 segundos) de delay que deberían ser suficientes para que el servidor
            //haga cualquier actualización que se le haya pedido antes de leer las órdenes.
            // Extraído del script.js utilizado como ejemplo en https://docs.angularjs.org/api/ngRoute/service/$route
            delay: function ($q, $timeout) {
                var delay = $q.defer();
                $timeout(delay.resolve, 100);
                return delay.promise;
            }
        }
    })
        .when("/editUser", {
            controller: "EditCtrl",
            controllerAs: "vm",
            templateUrl: "editUser.html",
            resolve: {
                // provoca 100 milisegundos (0,1 segundos) de delay que deberían ser suficientes para que el servidor
                //haga cualquier actualización que se le haya pedido antes de leer las órdenes.
                // Extraído del script.js utilizado como ejemplo en https://docs.angularjs.org/api/ngRoute/service/$route
                delay: function ($q, $timeout) {
                    var delay = $q.defer();
                    $timeout(delay.resolve, 100);
                    return delay.promise;
                }
            }
        })
        .when("/user/:ID", {
            controller: "PanelCtrl",
            controllerAs: "vm",
            templateUrl: "panelusuario.html"
        })
        .when("/comment/:ID", {
            controller: "EditCommentCtrl",
            controllerAs: "vm",
            templateUrl: "editComentario.html"
        })
        .when("/newcomment/:ID", {
            controller: "EditCommentCtrl",
            controllerAs: "vm",
            templateUrl: "editComentario.html"
        })
        .when("/newmensaje/:ID", {
            controller: "MensajeCtrl",
            controllerAs: "vm",
            templateUrl: "editMensaje.html"
        })
        .when("/users", {
            controller: "ListCtrl",
            controllerAs: "vm",
            templateUrl: "listarUsuarios.html"
        })
        .when("/favorito/:delete", {
            controller: "FavoritoCtrl",
            controllerAs: "vm",
            templateUrl: "Favorito.html"
        })
        .otherwise({
            templateUrl: "error.html"
        })

});