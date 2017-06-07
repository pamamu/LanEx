/**
 * Created by pablomaciasmu on 17/5/17.
 */

angular.module('lanexApp').controller("mainAppCtrl", function (usersFactory) {
    var vm = this;
    vm.user = {};
    vm.funciones = {
        obtenerUsuario: function () {
            usersFactory.leerUser()
                .then(function (respuesta) {
                    vm.user = respuesta;
                    console.log("Trayendo user con id: ", vm.user.idu, " - ", vm.user.username, " Respuesta: ", respuesta);
                }, function (respuesta) {
                    console.log("Error:", respuesta.status);
                })
        }
    };
    vm.funciones.obtenerUsuario();
})
    .controller("PanelCtrl", function (usersFactory, usersLanguagesFactory, favoritosFactory, comentariosFactory,
                                       mensajesFactory, $location, $routeParams, $window) {
        var vm = this;
        vm.user = {};
        vm.userLangsNames = {};
        vm.votado = false;
        vm.userLangs = {};
        vm.comentariosRecibidos = {};
        vm.comentariosEnviados = {};
        vm.mensajesRecibidos = {};
        vm.mensajesEnviados = {};
        vm.favoritos = {};
        vm.funciones = {
            obtenerUsuario: function () {
                usersFactory.leerUser()
                    .then(function (respuesta) {
                        vm.user = respuesta;
                        vm.userlogued = respuesta.idu;
                        console.log("Trayendo user con id: ", vm.user.idu, " Respuesta: ", respuesta);
                        vm.funciones.obtenerFavoritos();
                        vm.funciones.obtenerComentarios();
                        vm.funciones.obtenerMensajes();
                        vm.funciones.obtenerUserLangs(respuesta.idu);
                        vm.panelcontrol = true;
                    }, function (respuesta) {
                        console.log("Error:", respuesta.status);
                    })
            },
            obtenerUsuarioId: function (id) {
                usersFactory.getUser(id)
                    .then(function (respuesta) {
                        usersFactory.leerUser().then(function (respuesta2) {
                            vm.userlogued = respuesta2.idu;
                            vm.user = respuesta;
                            vm.panelcontrol = false;
                            if (vm.user.idu == vm.userlogued)
                                $location.path('/');
                            console.log("Trayendo user con id: ", vm.user.idu, " Respuesta: ", respuesta);
                        });
                        vm.funciones.obtenerComentariosId(id);
                        vm.funciones.obtenerUserLangs(id);
                    }, function (respuesta) {
                        console.log("Error:", respuesta.status);
                    })
            },
            obtenerUserLangs: function (id) {
                usersLanguagesFactory.leerUserLangsID(id)
                    .then(function (respuesta) {
                        vm.userLangs = respuesta;
                        console.log("Trayendo userLangs de usuario: ", id, " Respuesta: ", respuesta);
                    }, function (respuesta) {
                        console.log("Error:", respuesta.status);
                    });
                usersLanguagesFactory.leerUserLangsNameID(id)
                    .then(function (respuesta) {
                        vm.userLangsNames = respuesta;
                        console.log("Trayendo userLangsNames de usuario " + id + ": Respuesta: ", respuesta);
                    }, function (respuesta) {
                        console.log("Error:", respuesta.status);
                    })
            },
            obtenerFavoritos: function () {
                favoritosFactory.leerFavoritos()
                    .then(function (respuesta) {
                        vm.favoritos = respuesta;
                        console.log("Trayendo favoritos de usuario: Respuesta: ", respuesta);
                    }, function (respuesta) {
                        console.log("Error:", respuesta.status);
                    })
            },
            obtenerComentarios: function () {
                comentariosFactory.leerComentariosRecibidos()
                    .then(function (respuesta) {
                        console.log("Comentario recibidos R: ", respuesta);
                        vm.comentariosRecibidos = respuesta;
                        angular.forEach(vm.comentariosRecibidos, function (comment) {
                            usersFactory.getUser(comment.sender).then(function (user) {
                                comment.emisor = user;
                            })
                        });
                    }, function (respuesta) {
                        console.log("Error:", respuesta.status);
                    });
                comentariosFactory.leerComentariosEnviados()
                    .then(function (respuesta) {
                        console.log("Comentario enviados R: ", respuesta);
                        vm.comentariosEnviados = respuesta;
                        angular.forEach(vm.comentariosEnviados, function (comment) {
                            usersFactory.getUser(comment.receiver).then(function (user) {
                                comment.receptor = user;
                            })
                        });
                    }, function (respuesta) {
                        console.log("Error:", respuesta.status);
                    });
            },
            obtenerComentariosId: function (id) {
                comentariosFactory.leerComentariosRecibidosId(id)
                    .then(function (respuesta) {
                        console.log("Comentario recibidos RID: ", respuesta);
                        vm.comentariosRecibidos = respuesta;
                        angular.forEach(vm.comentariosRecibidos, function (comment) {
                            usersFactory.getUser(comment.sender).then(function (user) {
                                comment.emisor = user;
                            })
                        });
                    }, function (respuesta) {
                        console.log("Error:", respuesta.status);
                    });
                comentariosFactory.leerComentariosEnviadosId(id)
                    .then(function (respuesta) {
                        console.log("Comentario enviados RID: ", respuesta);
                        vm.comentariosEnviados = respuesta;
                        angular.forEach(vm.comentariosEnviados, function (comment) {
                            usersFactory.getUser(comment.receiver).then(function (user) {
                                comment.receptor = user;
                            })
                        });
                    }, function (respuesta) {
                        console.log("Error:", respuesta.status);
                    });
            },
            obtenerMensajes: function () {
                mensajesFactory.leerMensajesRecibidos()
                    .then(function (respuesta) {
                        vm.mensajesRecibidos = respuesta;
                        angular.forEach(vm.mensajesRecibidos, function (mensaje) {
                            usersFactory.getUser(mensaje.sender).then(function (user) {
                                mensaje.emisor = user;
                            })
                        })
                    }, function (respuesta) {
                        console.log("Error:", respuesta.status);
                    });
                mensajesFactory.leerMensajesEnviados()
                    .then(function (respuesta) {
                        vm.mensajesEnviados = respuesta;
                        angular.forEach(vm.mensajesEnviados, function (mensaje) {
                            usersFactory.getUser(mensaje.receiver).then(function (user) {
                                mensaje.receptor = user;
                            })
                        })
                    });
            },
            borrarUsuario: function () {
                usersFactory.borrarUser(vm.user.idu)
                    .then(function (respuesta) {
                        console.log("Eliminando user con id:", vm.user.idu, " Respuesta recibida:", respuesta);
                    }, function () {
                        console.log("Error: eliminación de usuario no realizada");
                    });
                $window.location.href = 'login';
            },
            borrarComentario: function (id) {
                comentariosFactory.borrarComentario(id)
                    .then(function (respuesta) {
                        $window.alert("COMENTARIO BORRADO");
                        vm.funciones.obtenerComentariosId(vm.user.idu);
                    }, function () {
                        console.log("Error: eliminación de comentario no realizada");
                        $window.alert("ERROR EN COMENTARIO");
                    });
            },
            modificarpuntos: function (puntos) {
                usersFactory.subirpuntos(vm.user.idu, puntos)
                    .then(function (respuesta) {
                        vm.votado = true;
                        vm.user.puntos += puntos;
                        $window.alert("VOTADO");
                    }, function () {
                        $window.alert("ERROR EN VOTACIÓN");
                    })
            }
        };
        if (angular.isUndefined($routeParams.ID))
            vm.funciones.obtenerUsuario();
        else
            vm.funciones.obtenerUsuarioId($routeParams.ID);
    })
    .controller("EditCtrl", function (usersFactory, languagesFactory, usersLanguagesFactory, $routeParams, $location) {
            var vm = this;
            vm.user = {};
            vm.user.password = "";
            vm.modificar = false;
            vm.userLangsNames = {};
            vm.userLangs = [];
            vm.langedit = {};
            vm.languages = [];
            vm.nombres = [];
            //noinspection JSUnusedGlobalSymbols
            vm.funciones = {
                estoy: function (ruta) {
                    console.log("RUTA:", $location.path());
                    return $location.path() == ruta;
                },
                cambiarImagen: function () {
                    var x = document.getElementById("select_imagen").value;
                    var y = "/exchange/images/personas/img_avatar";
                    document.getElementById("img_perfil").src = y + x + ".png";
                },
                editarUsuario: function () {
                    usersFactory.putUser(vm.user)
                        .then(function (respuesta) {
                            console.log("OK CAMBIADO:", respuesta.status);
                            $location.path('/');
                        }, function (respuesta) {
                            console.log("WRONG CAMBIADO:", respuesta.status);
                        })

                },
                obtenerUsuario: function () {
                    usersFactory.leerUser()
                        .then(function (respuesta) {
                            vm.user = respuesta;
                            vm.user.imagen = vm.user.imagen.toString();
                            console.log("Trayendo user con id: ", vm.user.idu, " Respuesta: ", respuesta);
                        }, function (respuesta) {
                            console.log("Error:", respuesta.status);
                        });
                    languagesFactory.leerLanguages()
                        .then(function (respuesta) {
                            vm.languages = respuesta;
                            vm.funciones.obtenerUserLangs();
                            console.log("Trayendo lenguajes, Respuesta: ", respuesta);
                        }, function (respuesta) {
                            console.log("Error:", respuesta.status);
                        })

                }, idioma_elegido: function (language) {
                    var i = 0;
                    if (angular.isUndefined(language))
                        return false;
                    angular.forEach(vm.userLangs, function (userlang) {
                        if (angular.equals(language.idl, userlang.idl)) {
                            i++;
                        }
                    });
                    return i <= 0;

                },
                add_idioma: function (idioma) {
                    console.log("ULV:", idioma);
                    if (idioma == null)
                        return;
                    vm.userL.idl = idioma.idl;
                    vm.userL.idu = vm.user.idu;
                    vm.nombres.push(idioma.langname);
                    vm.userLangs.push(angular.copy(vm.userL));
                    usersLanguagesFactory.addUserLangs(vm.userL)
                        .then(function (respuesta) {
                            console.log("AGREGADO OK:", respuesta);
                            vm.funciones.obtenerUserLangs();
                        }, function (respuesta) {
                            console.log("AGREGADO OK:", respuesta);

                        });
                    //vm.userLangs.push(idioma);
                    console.log("ULV:", vm.userLangs);
                    //console.log("ULV:", vm.userLangs);
                },
                delete_idioma: function (indice) {
                    usersLanguagesFactory.borrarUserLangs(vm.userLangs[indice])
                        .then(function (respuesta) {
                            console.log("OK:", respuesta);
                            vm.nombres.splice(indice, 1);
                            vm.userLangs.splice(indice, 1);
                        }, function (respuesta) {
                            console.log("ErrorAA:", respuesta);
                        });
                },
                modificar_idioma: function (indice) {
                    console.log("userLangs:", vm.userLangs[indice]);
                    vm.modificar = true;
                    vm.langedit = vm.nombres[indice];
                    vm.userL.idl = vm.userLangs[indice].idl;
                    vm.userL.levelSpeaking = vm.userLangs[indice].levelSpeaking;
                    vm.userL.levelWriting = vm.userLangs[indice].levelWriting;
                    vm.userL.levelReading = vm.userLangs[indice].levelReading;
                    vm.userL.idu = vm.userLangs[indice].idu;
                    console.log("userL:", vm.userL);
                },
                modificar_idioma_fin: function () {
                    console.log("Modifica userL:", vm.userL);
                    vm.modificar = false;
                    usersLanguagesFactory.modificaUserLangs(vm.userL)
                        .then(function (respuesta) {
                            console.log("MODIFICADO OK:", respuesta);
                            vm.funciones.obtenerUserLangs();
                        }, function (respuesta) {
                            console.log("MODIFICADO WRONG:", respuesta);
                        });
                },
                obtenerUserLangs: function () {
                    usersLanguagesFactory.leerUserLangs()
                        .then(function (respuesta) {
                            vm.userLangs = respuesta;
                            console.log("Trayendo userLangs de usuario: ", vm.user.idu, " Respuesta: ", respuesta);
                        }, function (respuesta) {
                            console.log("ErrorBB:", respuesta.status);
                        });
                    usersLanguagesFactory.leerUserLangsName()
                        .then(function (respuesta) {
                            vm.nombres = respuesta;
                            console.log("Trayendo userLangsNames de usuario: Respuesta: ", respuesta);
                        }, function (respuesta) {
                            console.log("Error:AA", respuesta.status);
                        })
                }
            };

            if (vm.funciones.estoy(('/editUser'))) {
                console.log("EDITAAAANDO");
                vm.funciones.obtenerUsuario();
            }

        }
    ).controller("EditCommentCtrl", function (usersFactory, comentariosFactory, $location, $routeParams, $window) {
    var vm = this;
    vm.comment = {};
    vm.user = {};
    vm.userlogued = {};
    vm.error = {};
    vm.error.estado = false;
    vm.edicion = false;
    vm.funciones = {
        estoy: function (ruta) {
            return $location.path() == ruta;
        },
        obtenerUsuarioId: function (id) {
            usersFactory.getUser(id)
                .then(function (respuesta) {
                    usersFactory.leerUser().then(function (respuesta2) {
                        vm.userlogued = respuesta2;
                    });
                    vm.user = respuesta;
                    console.log("Trayendo user con id: ", vm.user.idu, " Respuesta: ", respuesta);
                }, function (respuesta) {
                    vm.error.estado = true;
                    vm.error.texto = "USUARIO NO EXISTE";
                    console.log("Error:", respuesta.status);
                })
        },
        obtenerComentario: function (id) {
            comentariosFactory.getComentario(id)
                .then(function (respuesta) {
                    console.log("CommentAA:", respuesta);
                    vm.comment = respuesta;
                    vm.funciones.obtenerUsuarioId(vm.comment.receiver);
                }, function () {
                    vm.error.estado = true;
                    vm.error.texto = "COMENTARIO NO ACCESIBLE";
                })
        },
        cambiarComentario: function () {
            comentariosFactory.actualizarComentario(vm.comment);
            $window.alert("COMENTARIO ACTUALIZADO");
            $window.history.back();
        },
        nuevoComentario: function () {
            vm.comment.sender = vm.userlogued.idu;
            vm.comment.receiver = vm.user.idu;
            comentariosFactory.addComentario(vm.comment);
            $window.alert("COMENTARIO CREADO");
            $window.history.back();
        },
        editarComentario: function () {
            if (vm.error.estado)
                return 0;
            else if (vm.edicion) {
                vm.funciones.cambiarComentario();
            }
            else {
                vm.funciones.nuevoComentario();
            }
        }
    };

    if (vm.funciones.estoy("/comment/" + $routeParams.ID)) {
        vm.funciones.obtenerComentario($routeParams.ID);
        vm.edicion = true;
    }
    else {
        vm.funciones.obtenerUsuarioId($routeParams.ID);
        vm.edicion = false;
    }


})
    .controller("ListCtrl", function (usersFactory, usersLanguagesFactory, languagesFactory) {
        var vm = this;
        vm.users = [];
        vm.languages_names = [];
        vm.searchuser = {};
        vm.skill = {};
        vm.orden = {};
        vm.funciones = {
            getUsuarios: function () {
                usersFactory.getAll()
                    .then(function (respuesta) {
                        vm.users = respuesta;
                        //console.log("Users: ", respuesta);
                        angular.forEach(vm.users, function (user) {
                            vm.funciones.obtenerUserLangs(user);
                        });
                        console.log("USERS:", vm.users);
                    });
                languagesFactory.leerLanguages()
                    .then(function (respuesta) {
                        vm.languages_names = respuesta;
                    })
            },
            obtenerUserLangs: function (user) {
                usersLanguagesFactory.leerUserLangsID(user.idu)
                    .then(function (respuesta) {
                        user.languages = respuesta;
                        //console.log("Trayendo userLangs de usuario: ", id, " Respuesta: ", respuesta);
                    }, function (respuesta) {
                        console.log("Error:", respuesta.status);
                    });
                usersLanguagesFactory.leerUserLangsNameID(user.idu)
                    .then(function (respuesta) {
                        user.languagesnames = respuesta;
                        //console.log("Trayendo userLangsNames de usuario " + id +": Respuesta: ", respuesta);
                    }, function (respuesta) {
                        console.log("Error:", respuesta.status);
                    });
                //console.log("Usuario: ", user);

            },
            reiniciarfiltro: function () {
                vm.searchuser = {};
                vm.skill.levelReading = undefined;
                vm.skill.levelSpeaking = undefined;
                vm.skill.levelWriting = undefined;
                vm.orden = undefined;
                vm.funciones.getUsuarios();
            },
            mostrar: function () {
                console.log("SEARCHUSER:", vm.skill);
                usersLanguagesFactory.getSkills(vm.skill.levelWriting, vm.skill.levelSpeaking, vm.skill.levelReading)
                    .then(function (respuesta) {
                        vm.users = [];
                        angular.forEach(respuesta, function (idu) {
                            usersFactory.getUser(idu).then(function (r_user) {
                                vm.funciones.obtenerUserLangs(r_user);
                                vm.users.push(r_user);
                            })
                        });
                        console.log("USERS:", vm.users);
                        console.log("USERSaaa:", vm.searchuser);
                    })

            }
        };
        vm.funciones.getUsuarios();

    })
    .controller("FavoritoCtrl", function (usersFactory, favoritosFactory, $location, $window, $routeParams) {
        var vm = this;
        vm.favoritos = [];
        vm.borrado = false;
        vm.nofavoritos = [];
        vm.funciones = {
            obtenerFavoritos: function () {
                favoritosFactory.leerFavoritos()
                    .then(function (response) {
                        vm.favoritos = response;
                    });
            },
            obtenerNoFavoritos: function () {
                favoritosFactory.leerNoFavoritos()
                    .then(function (respuesta) {
                        vm.nofavoritos = respuesta;
                    })
            },
            eliminarFavorito: function (user) {
                console.log("ELIMINA ", user)
                favoritosFactory.eliminarFavorito(user.username)
                    .then(function () {
                        $window.alert("FAVORITO BORRADO");
                        $location.path('/');
                    }, function (respuesta) {
                        console.log("ERROR", respuesta.data)
                    });
            },
            addFavorito: function (user) {
                console.log("add ", user)
                favoritosFactory.addFavorito(user)
                    .then(function () {
                        $window.alert("FAVORITO AÑADIDO");
                        $location.path('/');
                    }, function (respuesta) {
                        console.log("ERROR", respuesta.data);
                    })

            }
        };
        if ($routeParams.delete == "delete") {
            vm.funciones.obtenerFavoritos();
            vm.borrado = true;
        }
        else
            vm.funciones.obtenerNoFavoritos();
    })
    .controller("MensajeCtrl", function (usersFactory, mensajesFactory, $window, $routeParams) {
        var vm = this;
        vm.mensaje = {};
        vm.userlogued = {};
        vm.user = {};
        vm.error = {};
        vm.error.estado = false;
        vm.funciones = {
            obtenerUsuarioId: function (id) {
                usersFactory.getUser(id)
                    .then(function (respuesta) {
                        usersFactory.leerUser().then(function (respuesta2) {
                            vm.userlogued = respuesta2;
                            console.log("AAAAAAAAA", respuesta2);
                            vm.user = respuesta;
                            if (vm.userlogued.idu == id){
                                vm.error.estado = true;
                                vm.error.texto = "ERROR - MENSAJE A SÍ MISMO";
                            }
                        });
                        // console.log("Trayendo user con id: ", vm.user.idu, " Respuesta: ", respuesta);
                        // console.log("Trayendo user logueado: ", vm.userlogued.idu, " Respuesta: ", vm.userlogued);
                    }, function (respuesta) {
                        vm.error.estado = true;
                        vm.error.texto = "USUARIO NO EXISTE";
                        console.log("Error:", respuesta.status);
                    })
            },
            nuevoMensaje: function () {
                vm.mensaje.sender = vm.userlogued.idu;
                vm.mensaje.receiver = vm.user.idu;
                mensajesFactory.crearMensaje(vm.mensaje);
                $window.alert("MENSAJE ENVIADO A " + vm.user.username);
                $window.history.back();
            }
        };
        vm.funciones.obtenerUsuarioId($routeParams.ID);


    });