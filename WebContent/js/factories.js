/**
 * Created by pablomaciasmu on 17/5/17.
 */

angular.module('lanexApp')
    .factory("usersFactory", function ($http) {
        var url = 'https://localhost:8443/exchange/rest/users/me$';
        var url2 = 'https://localhost:8443/exchange/rest/users/';
        var interfaz = {
            leerUser: function () {
                return $http.get(url)
                    .then(function (response) {
                        return response.data;
                    });
            },
            getUser: function (id) {
                var urlid = url2 + id;
                return $http.get(urlid)
                    .then(function (response) {
                        return response.data;
                    });
            },
            borrarUser: function (id) {
                var urlid = url2 + id;
                return $http.delete(urlid)
                    .then(function (response) {
                        return response.status;
                    })
            },
            putUser: function (user) {
                return $http.put(url2, user)
                    .then(function (response) {
                        return response.status;
                    })
            },
            getAll: function () {
                return $http.get(url2)
                    .then(function (response) {
                        return response.data;
                    })
            },
            subirpuntos: function (id, puntos) {
                var url3 = url2 + id + "/p?puntos=" + puntos;
                return $http.post(url3)
                    .then(function (response) {
                        return response.data;
                    })
            }
        };
        return interfaz;

    }).factory("usersLanguagesFactory", function ($http) {
    var url = 'https://localhost:8443/exchange/rest/userlanguage';
    var url2 = 'https://localhost:8443/exchange/rest/userlanguage/n';
    var interfaz = {
        leerUserLangs: function () {
            return $http.get(url)
                .then(function (response) {
                    return response.data;
                });
        }, leerUserLangsID: function (id) {
            var urlid = url + '/' + id;
            return $http.get(urlid)
                .then(function (response) {
                    return response.data;
                });
        }, leerUserLangsNameID: function (id) {
            var urlid = url2 + '/' + id;
            return $http.get(urlid)
                .then(function (response) {
                    return response.data;
                });
        },
        leerUserLangsName: function () {
            return $http.get(url2)
                .then(function (response) {
                    return response.data;
                });
        },
        addUserLangs: function (userlang) {
            return $http.post(url2, userlang)
                .then(function (response) {
                    return response.status;
                });
        },
        modificaUserLangs: function (userlang) {
            return $http.put(url, userlang)
                .then(function (response) {
                    return response.status;
                });
        },
        borrarUserLangs: function (userlang) {
            var url3 = url + '/' + userlang.idu + '/' + userlang.idl;
            console.log(url3);
            return $http.delete(url3)
                .then(function (response) {
                    return response.status;
                })
        },
        getSkills: function (lw, ls, lr) {
            if (angular.isUndefined(lw))
                lw = "";
            if (angular.isUndefined(ls))
                ls = "";
            if (angular.isUndefined(lr))
                lr = "";
            var urls = url + '/' + lw + '/' + ls + '/' + lr;
            console.log(urls);
            return $http.get(urls)
                .then(function (response) {
                        return response.data;
                    }
                )
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

}).factory("favoritosFactory", function ($http) {
    var url = 'https://localhost:8443/exchange/rest/favorito';
    var interfaz = {
        leerFavoritos: function () {
            return $http.get(url)
                .then(function (response) {
                    return response.data;
                });
        },
        leerNoFavoritos: function () {
            var url2 = url + '/n';
            return $http.get(url2)
                .then(function (response) {
                    return response.data;
                });
        },
        addFavorito:function (user) {
            return $http.post(url, user)
                .then(function (response) {
                    return response.data;
                });
        },
        eliminarFavorito:function (username) {
            var url2 = url + "/" + username;
            return $http.delete(url2)
                .then(function (response) {
                    return response.data;
                });
        }
    };
    return interfaz;

}).factory("comentariosFactory", function ($http) {
    var url = 'https://localhost:8443/exchange/rest/comments/';
    var url2 = 'https://localhost:8443/exchange/rest/comments/send';
    var interfaz = {
        leerComentariosRecibidos: function () {
            return $http.get(url)
                .then(function (response) {
                    return response.data;
                });
        },
        leerComentariosEnviados: function () {
            return $http.get(url2)
                .then(function (response) {
                    return response.data;
                });
        }, leerComentariosRecibidosId: function (id) {
            var urlid = url + 'receptor?id=' + id;
            return $http.get(urlid)
                .then(function (response) {
                    return response.data;
                });
        },
        leerComentariosEnviadosId: function (id) {
            var urlid = url + 'emisor?id=' + id;
            return $http.get(urlid)
                .then(function (response) {
                    return response.data;
                });
        },
        borrarComentario: function (id) {
            var urlid = url + id;
            return $http.delete(urlid)
                .then(function (response) {
                    return response.data;
                });
        },
        getComentario: function (id) {
            var urlid = url + id;
            return $http.get(urlid)
                .then(function (response) {
                    return response.data;
                })
        },
        addComentario: function (commment) {
            return $http.post(url, commment)
                .then(function (response) {
                    return response.data;
                })
        },
        actualizarComentario: function (comment) {
            return $http.put(url, comment)
                .then(function (response) {
                    return response.data;
                })
        }
    };
    return interfaz;
}).factory("mensajesFactory", function ($http) {
    var url = 'https://localhost:8443/exchange/rest/mensajes';
    var url2 = 'https://localhost:8443/exchange/rest/mensajes/emisor';
    var interfaz = {
        leerMensajesRecibidos: function () {
            return $http.get(url)
                .then(function (response) {
                    return response.data;
                });
        },
        leerMensajesEnviados: function () {
            return $http.get(url2)
                .then(function (response) {
                    return response.data;
                });
        },
        crearMensaje: function (mensaje) {
            return $http.post(url, mensaje)
                .then(function (response) {
                    return response.data;
                })
        }
    };
    return interfaz;
})