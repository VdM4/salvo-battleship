$(document).ready(function(){


     $.ajax({

        type: 'Get',
        url: 'http://localhost:8080/api/game_view/' + getParameterUrl()

        }).done (function (data) {

            //alert('Success');
            createEmptyGrid();
            createEmptySalvoGrid();
            var gameData = data;
            console.log(gameData);
            createConditionalGrid(gameData);
            showGamePlayerInfo(gameData);
            fireShots(gameData);
            checkForShips(gameData);



        })
        .fail (function (){
            alert ("Loser, try again next year!");
        })

});

function createEmptyGrid(){

         var letterArray = ["","A","B","C","D","E","F","G","H","I","J"];
         var numberArray = ["","1","2","3","4","5","6","7","8","9","10"];

        var grid = $('#grid');



       for (var i = 0; i < numberArray.length; i++){

            var row = $('<div class="row"/>');

            for (var j = 0; j < letterArray.length; j++){


                var tile = $('<div class="tile"/>').addClass(letterArray[j] + numberArray[i]);

                if (i === 0){
                tile.html(letterArray[j]).addClass("specialTiles");
                };
                if (j === 0){
                tile.html(numberArray[i]).addClass("specialTiles");
                };

                row.append(tile);
            }
            grid.append(row);
       }
}

function getParameterUrl(){

      /*if ((window.location.search).substring(0, 4) === “?gp=“){
             return (window.location.search).substring(4, (window.location.search).length);
      }*/

       var id = window.location.search;

       return id.substring(4);

}

function createConditionalGrid(gameData){

    var ships = gameData.ships;
    //console.log(ships);

    for (var i = 0; i < ships.length; i++){

        var locations = ships[i].locations;
        //console.log(locations);

        for (var j = 0; j < locations.length; j++){

            var locationId = locations[j];
            //console.log(locationId);

            $('.' + locationId).addClass("ship");

        }
    }
}

function showGamePlayerInfo(gameData){

        var participation = gameData.participation;
        //console.log(participation);

        for (var i = 0; i < participation.length; i++){


            var id = participation[i].id;
            //console.log(id);
            var playerEmail = participation[i].player.username;
            //console.log(playerEmail);

            var $gamePlayerID = $('<div class="playerID/>');
            var $playerUsername = $('<div class="playerEmail"/>');

            $gamePlayerID.append(id);
            $playerUsername.append(playerEmail);
            $('.playerInfo').append($gamePlayerID)
                            .append($playerUsername);


        }



}

function createEmptySalvoGrid(){

          var letterArray = ["","A","B","C","D","E","F","G","H","I","J"];
          var numberArray = ["","1","2","3","4","5","6","7","8","9","10"];

                var grid = $('#salvoGrid');



               for (var i = 0; i < numberArray.length; i++){

                    var row = $('<div class="row"/>');

                    for (var j = 0; j < letterArray.length; j++){


                        var tile = $('<div class="tile"/>').addClass("alt" + letterArray[j] + numberArray[i]);

                        if (i === 0){
                        tile.html(letterArray[j]).addClass("specialTiles2");
                        };
                        if (j === 0){
                        tile.html(numberArray[i]).addClass("specialTiles2");
                        };

                        row.append(tile);
                    }
                    grid.append(row);
               }



}

function showShotsFired(gameData,div,i){


    var salvos = gameData.salvoes[i].salvos;

       for (var j = 0; j < salvos.length; j++){

            var locations = salvos[j].locations;
            //console.log(locations);
            var turn = salvos[j].turn;
            //console.log(turn);

            for (var z = 0; z < locations.length; z++){

                var shots = locations[z]
                //console.log(shots);


                if($('.' + div + shots).hasClass("alt" + shots) == true){
                    $('.' + div + shots).addClass("firedUSSR")
                    $('.'+ div + shots).append(turn)
                }
                else {
                    $('.' + div + shots).addClass("firedUSA")
                    $('.'+ div + shots).append(turn)
                };


                if($('.'+ div + shots).hasClass("ship")){
                    $('.'+ div + shots).addClass("boatHit")
                };

            }

        }
    }

function fireShots(gameData){

    var salvoes = gameData.salvoes;

    for (var i = 0; i < salvoes.length; i++){

        var players = Number(salvoes[i].player);
        var param = Number(getParameterUrl());
        //console.log(players);

        if (players == param){
            showShotsFired(gameData, 'alt',i);
        }
        else {
            showShotsFired(gameData, "",i);
        }


    }

}

function jsonShips(gameData){

    $.post({

        url:"/api/games/players/" + gpId + "/ships",
        data: JSON.stringify({shipType: type, shipLocations: locations }),
        contentType: "application/json"
    })

    .done(function(response, status, jqXHR){
        alert("Ship added" + response);
    })

    .fail(function(jqXHR, status, httpError){
        alert("Failed to add ship: " + status + httpError);
    })

//    var constructor = [ { "shipType": "destroyer", "locations": ["A1", "B1", "C1"] },
//                        { "shipType": "patrol boat", "locations": ["H5", "H6"] }
//                      ]


}

function checkForShips(gameData){

        console.log(gameData);
    var ships = gameData.ships;

    console.log(ships);



        if(ships.length == 0){
                $('#salvoGrid').css("display", "none")
            };






}




function logOutButton(evt){
  evt.preventDefault();
  $.post("/api/logout")

   .done(function() {alert("You have successfully logged out")
        $("#logOut").css("display", "none")

   })

}

$("#logOut").click(function(evt) {
        logOutButton(evt);
    })





