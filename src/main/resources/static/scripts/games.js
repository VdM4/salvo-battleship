getJson();

function getJson(){

    var games = "api/games";

    $.getJSON(games, function (data){

    var allData = data.Games

    console.log(allData);

    createList(allData);

    })
}

function createList(allData){

    console.log(allData);

    $.each(allData, function(key,value){

        var $games = $('<ul class="gamesLevel"/>');
        var $gameId = $('<li class="gameID"/>');
        var $gameDate = $('<li class="gameDate"/>');
        var $empty1 = $('<li class="empty"/>');
        var $empty2 = $('<li class="empty"/>');
        var $participation1 = $('<ul class="particip1"/>');
        var $participation2 = $('<ul class="particip2"/>');
        var $gamePlayer1 = $('<li class="gamePlayerID1"/>');
        var $gamePlayer2 = $('<li class="gamePlayerID2"/>');
        var $player1 = $('<ul class="player1"/>');
        var $player2 = $('<ul class="player2"/>');
        var $playerId1 = $('<li class="playerID1"/>');
        var $playerId2 = $('<li class="playerID2"/>');
        var $username1 = $('<li class="username1"/>');
        var $username2 = $('<li class="username2"/>');

        var game = "Game";
        var gameId = "Game" + value.id;
        var gameDate = new Date(value.creationDate);
        var parti = value.participation;
        var gamepl1 = "Participant" + " " + value.participation[0].id;
        var gamepl2 = "Participant" + " " + value.participation[1].id;
        var player1 = value.participation[0].player;
        var player2 = value.participation[1].player;
        var playerId1 = value.participation[0].player.id;
        var playerId2 = value.participation[1].player.id;
        var username1 = value.participation[0].player.username;
        var username2 = value.participation[1].player.username;

        $username2.append(username2);
        $username1.append(username1);
        $playerId2.append(playerId2);
        $playerId1.append(playerId1);
        $player2.append($username2)
                .append($playerId2);
        $player1.append($username1)
                .append($playerId1);
        $gamePlayer2.append(gamepl2);
        $gamePlayer1.append(gamepl1);
        $gamePlayer2.append($player2);
        $gamePlayer1.append($player1);
        $participation2.append($gamePlayer2);
        $participation1.append($gamePlayer1);
        $empty2.append($participation2);
        $empty1.append($participation1);
        $gameDate.append(gameDate);
        $gameId.append(gameId);
        $games.append($gameId)
              .append($gameDate)
              .append($empty1)
              .append($empty2);


        $('.List').append($games);

    });
}