$(document).ready(function () {
    var json = {};
    var Initials = $('.initials');
    var LetterBox = $('#letter');
    Initials.find('ul').append('<li>#</li><li>A</li><li>B</li><li>C</li><li>D</li><li>E</li><li>F</li><li>G</li><li>H</li><li>I</li><li>J</li><li>K</li><li>L</li><li>M</li><li>N</li><li>O</li><li>P</li><li>Q</li><li>R</li><li>S</li><li>T</li><li>U</li><li>V</li><li>W</li><li>X</li><li>Y</li><li>Z</li>');
    $(".initials ul li").click(function () {
        $(this).css("color", "#1E90FF").siblings().css("color", "#666666")


        var index = $(this).index();
        var offsetH = $(".list-con").eq(index - 1).offset().top;
        $("body").animate({
            "scrollTop": offsetH - 150
        }, 0);

    })

    var token = getcookie("sra");
    var res = getUrl("/suyh/app/7/findAllCityByInitials", "", "");
    if (res != "") {
        var result = JSON.parse(res);
        if (result.mark == "0") {
            var str = result.obj;
            var str2 = str.hot_city;
            var str3 = str.all_city;
            var arr = [];
            for (var i = 0; i < str2.length; i++) {
                arr = '<li class="hot-city hot-border">' + str2[i].code_name + "</li>";
                $(".nav").append(arr);

            }
            var A = [];
            var A1 = [];
            var B = [];
            var B1 = [];
            var C = [];
            var C1 = [];
            var D = [];
            var D1 = [];
            var E = [];
            var E1 = [];
            var F = [];
            var F1 = [];
            var G = [];
            var G1 = [];
            var H = [];
            var H1 = [];
            var I = [];
            var I1 = [];
            var J = [];
            var J1 = [];
            var K = [];
            var K1 = [];
            var L = [];
            var L1 = [];
            var M = [];
            var M1 = [];
            var N = [];
            var N1 = [];
            var O = [];
            var O1 = [];
            var P = [];
            var P1 = [];
            var Q = [];
            var Q1 = [];
            var R = [];
            var R1 = [];
            var S = [];
            var S1 = [];
            var T = [];
            var T1 = [];
            var U = [];
            var U1 = [];
            var V = [];
            var V1 = [];
            var W = [];
            var W1 = [];
            var X = [];
            var X1 = [];
            var Y = [];
            var Y1 = [];
            var Z = [];
            var Z1 = [];
            for (var i = 0; i < str3.length; i++) {
                if (str3[i].dict_initials == "A") {
                    A.push(str3[i]);
                }
                if (str3[i].dict_initials == "B") {
                    B.push(str3[i]);
                }
                if (str3[i].dict_initials == "C") {
                    C.push(str3[i]);
                }
                if (str3[i].dict_initials == "D") {
                    D.push(str3[i]);
                }
                if (str3[i].dict_initials == "E") {
                    E.push(str3[i]);
                }
                if (str3[i].dict_initials == "F") {
                    F.push(str3[i]);
                }
                if (str3[i].dict_initials == "G") {
                    G.push(str3[i]);
                }
                if (str3[i].dict_initials == "H") {
                    H.push(str3[i]);
                }
                if (str3[i].dict_initials == "I") {
                    I.push(str3[i]);
                }
                if (str3[i].dict_initials == "J") {
                    J.push(str3[i]);
                }
                if (str3[i].dict_initials == "K") {
                    K.push(str3[i]);
                }
                if (str3[i].dict_initials == "L") {
                    L.push(str3[i]);
                }
                if (str3[i].dict_initials == "M") {
                    M.push(str3[i]);
                }
                if (str3[i].dict_initials == "N") {
                    N.push(str3[i]);
                }
                if (str3[i].dict_initials == "O") {
                    O.push(str3[i]);
                }
                if (str3[i].dict_initials == "P") {
                    P.push(str3[i]);
                }
                if (str3[i].dict_initials == "Q") {
                    Q.push(str3[i]);
                }
                if (str3[i].dict_initials == "R") {
                    R.push(str3[i]);
                }
                if (str3[i].dict_initials == "S") {
                    S.push(str3[i]);
                }
                if (str3[i].dict_initials == "T") {
                    T.push(str3[i]);
                }
                if (str3[i].dict_initials == "U") {
                    U.push(str3[i]);
                }
                if (str3[i].dict_initials == "V") {
                    V.push(str3[i]);
                }
                if (str3[i].dict_initials == "W") {
                    W.push(str3[i]);
                }
                if (str3[i].dict_initials == "X") {
                    X.push(str3[i]);
                }
                if (str3[i].dict_initials == "Y") {
                    Y.push(str3[i]);
                }
                if (str3[i].dict_initials == "Z") {
                    Z.push(str3[i]);
                }
            }
            for (var a = 0; a < A.length; a++) {
                A1 = '<p class="cityname">' + A[a].code_name + '</p>';
                $('.a').append(A1);
            }
            for (var a = 0; a < B.length; a++) {
                B1 = '<p class="cityname">' + B[a].code_name + '</p>';
                $('.b').append(B1);
            }
            for (var a = 0; a < C.length; a++) {
                C1 = '<p class="cityname">' + C[a].code_name + '</p>';
                $('.c').append(C1);
            }
            for (var a = 0; a < D.length; a++) {
                D1 = '<p class="cityname">' + D[a].code_name + '</p>';
                $('.d').append(D1);
            }
            for (var a = 0; a < E.length; a++) {
                E1 = '<p class="cityname">' + E[a].code_name + '</p>';
                $('.e').append(E1);
            }
            for (var a = 0; a < F.length; a++) {
                F1 = '<p class="cityname">' + F[a].code_name + '</p>';
                $('.f').append(F1);
            }
            for (var a = 0; a < G.length; a++) {
                G1 = '<p class="cityname">' + G[a].code_name + '</p>';
                $('.g').append(G1);
            }
            for (var a = 0; a < H.length; a++) {
                H1 = '<p class="cityname">' + H[a].code_name + '</p>';
                $('.h').append(H1);
            }
            for (var a = 0; a < I.length; a++) {
                I1 = '<p class="cityname">' + I[a].code_name + '</p>';
                $('.i').append(I1);
            }
            for (var a = 0; a < J.length; a++) {
                J1 = '<p class="cityname">' + J[a].code_name + '</p>';
                $('.j').append(J1);
            }
            for (var a = 0; a < K.length; a++) {
                K1 = '<p class="cityname">' + K[a].code_name + '</p>';
                $('.k').append(K1);
            }
            for (var a = 0; a < L.length; a++) {
                L1 = '<p class="cityname">' + L[a].code_name + '</p>';
                $('.l').append(L1);
            }
            for (var a = 0; a < M.length; a++) {
                M1 = '<p class="cityname">' + M[a].code_name + '</p>';
                $('.m').append(M1);
            }
            for (var a = 0; a < N.length; a++) {
                N1 = '<p class="cityname">' + N[a].code_name + '</p>';
                $('.n').append(N1);
            }
            for (var a = 0; a < O.length; a++) {
                O1 = '<p class="cityname">' + O[a].code_name + '</p>';
                $('.o').append(O1);
            }
            for (var a = 0; a < P.length; a++) {
                P1 = '<p class="cityname">' + P[a].code_name + '</p>';
                $('.p').append(P1);
            }
            for (var a = 0; a < Q.length; a++) {
                Q1 = '<p class="cityname">' + Q[a].code_name + '</p>';
                $('.q').append(Q1);
            }
            for (var a = 0; a < R.length; a++) {
                R1 = '<p class="cityname">' + R[a].code_name + '</p>';
                $('.r').append(R1);
            }
            for (var a = 0; a < S.length; a++) {
                S1 = '<p class="cityname">' + S[a].code_name + '</p>';
                $('.s').append(S1);
            }
            for (var a = 0; a < T.length; a++) {
                T1 = '<p class="cityname">' + T[a].code_name + '</p>';
                $('.t').append(T1);
            }
            for (var a = 0; a < U.length; a++) {
                U1 = '<p class="cityname">' + U[a].code_name + '</p>';
                $('.u').append(U1);
            }
            for (var a = 0; a < V.length; a++) {
                V1 = '<p class="cityname">' + V[a].code_name + '</p>';
                $('.v').append(V1);
            }
            for (var a = 0; a < W.length; a++) {
                W1 = '<p class="cityname">' + W[a].code_name + '</p>';
                $('.w').append(W1);
            }
            for (var a = 0; a < X.length; a++) {
                X1 = '<p class="cityname">' + X[a].code_name + '</p>';
                $('.x').append(X1);
            }
            for (var a = 0; a < Y.length; a++) {
                Y1 = '<p class="cityname">' + Y[a].code_name + '</p>';
                $('.y').append(Y1);
            }
            for (var a = 0; a < Z.length; a++) {
                Z1 = '<p class="cityname">' + Z[a].code_name + '</p>';
                $('.z').append(Z1);
            }
        }
    }

    var city = sessionStorage.getItem('city')
    if (city != null) {
        $(".dingcity").html(city)
    }

    $("#searchbox").blur(function (){
        var json = {
            "code_name": $("#searchbox").val(),
            "code_type": "city"
        };
        var res = getUrl("/suyh/app/7/findDictByName", json, token);
        if (res != "") {
            var result = JSON.parse(res);
            if (result.mark == "0" && result.obj.length == "1") {
                var city = result.obj;
                var cityname = [];
                $(".mask").html("")
                cityname = '<p class="citynamep">' + city + '</p>';
                $(".mask").append(cityname)
                $(".mask").css("display", "block")
            }
        }
    })

    $("#sure").click(function () {
        var choose_city = sessionStorage.getItem('choose_city');
        if(choose_city == null || choose_city == ""){
            var storage = window.sessionStorage;
            storage.setItem("choose_city", city);
        }
        window.location.href = "chooseMapAddress.html";
    })
    $(".mask").click(function () {
        $(".mask").hide()
    })

    $(".hot-city").click(function (e) {
        var storage = window.sessionStorage;
        storage.setItem("choose_city", e.target.innerHTML);
        e.removeClass("hot-border").addClass("dingcity-border");
    })

    $(".cityname").click(function (e) {
        var storage = window.sessionStorage;
        storage.setItem("choose_city", e.target.innerHTML);
        window.location.href = "chooseMapAddress.html";
    })

    $(".dingcity").click(function (e) {
        var storage = window.sessionStorage;
        storage.setItem("choose_city", e.target.innerHTML);
        e.removeClass("hot-border").addClass("dingcity-border");
    })

})
