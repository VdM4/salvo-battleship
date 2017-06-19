$(document).ready(function(){

   getGames();
   eventListeners();


});

function getGames(){

      $.ajax({

            type: 'Get',
            url: 'http://localhost:8080/api/games'

            }).done (function (data) {

                //alert('Success');
                var allData = data;
                console.log(allData);
                createLeaderBoardTable(allData);
                checkPlayer(allData);
                showAllGameInfo(allData);





            })
            .fail (function (){
                alert ("This is Soviet Technology, did you really think it would work the first time?");
            })
}

function createTableHeader(){


var tblHeader = $('<thead/>');
var row = $('<tr/>');

row.append('<th class="tableHeader"> Username </th>');
row.append('<th class="tableHeader"> Wins </th>');
row.append('<th class="tableHeader"> Losses </th>');
row.append('<th class="tableHeader"> Ties </th>');
row.append('<th class="tableHeader"> Total Scores </th>');

tblHeader.append(row);
return tblHeader;

}

function createLeaderBoardTable(allData){

    $(".leaderboard").empty();
    //console.log(allData);
    var leaderBoard = allData.leaderboard;
    //console.log(leaderBoard);

   leaderBoard.sort(function(a,b){return b.totalScores - a.totalScores});

    var tblHead = createTableHeader();
    var tbl = $('<table id="leaderTable"/>');
    var tbody = $('<tbody/>');

    for(var i = 0; i < leaderBoard.length; i++){

         var player = leaderBoard[i];
         var row = $('<tr/>');
         var name = $('<td class="center">' + player.username +'</td>');
         var wins = $('<td class="center">' + player.wins +'</td>');
         var losses = $('<td class="center">' + player.losses +'</td>');
         var ties = $('<td class="center">' + player.ties + '</td>');
         var scores = $('<td class="center">' + player.totalScores + '</td>');

        row.append(name)
            .append(wins)
            .append(losses)
            .append(ties)
            .append(scores);

        tbody.append(row);

    }

    tbl.append(tblHead);
    tbl.append(tbody);

    $('.leaderboard').append(tbl);

}

function eventListeners(){

    $("#displayLogin").click(function(){
        $("#loginDiv").css("display", "block");
        $("#signInDiv").css("display", "none");
    })

    $("#loginButton").click(function(evt){
        login(evt);
    })

    $("#logOut").click(function(evt) {
        logout(evt);
    })

     $("#displaySignIn").click(function(){
          $("#signInDiv").css("display", "block");
          $("#loginDiv").css("display", "none");
     })

    $("#signButton").click(function(evt){
            signIn(evt);
        })

    $('#creation').click(function(){
           createGame();

     })

}

function login(evt) {
  evt.preventDefault();
  var form = evt.target.form;
  $.post("/api/login",
         { username: form["username"].value,
           password: form["password"].value })


   .done(function() {
        alert("logged in")
        $("#loginDiv").css("display", "none")
        $("#logOut").css("display", "block")
        getGames();

        })
   .fail(function() {alert("login failed")});
}

function logout(evt) {
  evt.preventDefault();
  $.post("/api/logout")

   .done(function() {alert("You have successfully logged out")
        $("#logOut").css("display", "none")

   })

}

function signIn(evt) {
    evt.preventDefault();
    var form = evt.target.form;
    $.post("/api/players",
        { userName: form["username"].value,
          password: form["password"].value })

        .done(function(a,b,c){
            alert(a);
            console.log(b);
            console.log(c);
            $.post("/api/login",
                     { username: form["username"].value,
                       password: form["password"].value });
            location.reload();


    })

    .fail(function(a) {
        console.log(a);
        alert(a.responseText);
    })

}

function checkPlayer(allData){

    var currentPlayer = allData.currentPlayer;

    if(currentPlayer) {
        console.log("is in array");
        $("#logOut").css("display", "block")

    } else {
        console.log("is NOT in array");
    }



}

function createGameHeader() {

    var tableHead = $('<thead/>');
    var row = $('<tr/>');

    row.append('<th class="gameTableHeader"> # </th>')
    row.append('<th class="gameTableHeader"> USSR </th>')
    row.append('<th class="gameTableHeader"> USA </th>')
    row.append('<th class="gameTableHeader"> Options </th>')

    tableHead.append(row);
    return tableHead;
}

function showAllGameInfo(allData) {

    $('.battleSelect').empty();

    var games = allData.games;
    //console.log(games);

    var currentPlayer = allData.currentPlayer.id;
    var currentPlayerGames = allData.currentPlayer.games;

     var tblHead = createGameHeader();
     var tbl = $('<table id="gameTable"/>');
     var tBody = $('<tBody/>');

     $.each(games, function(key, value){

        var row = $('<tr/>');
        var gameNumber = $('<td class="center">' + value.id +'</td>');
        var url;


        var participation = value.participation;

        row.append(gameNumber);

        var setButton = false;

                $.each(participation, function(key2, value2){

                    var players = $('<td class="center">' + value2.player.username +'</td>');

                    var playerID = value2.player.id;
                    var gpID = value2.id

                       if(currentPlayer == playerID){
                            setButton = true;
                            url = "/game.html?gp=" + gpID;

                       }
                        else if(currentPlayer != playerID){

                        }

                        row.append(players);

                })

        var joinButton = $('<td/>', {

            text: 'Join',
            id: 'join_game' + value.id,
            class: 'buttonCells btn btn-warning btn-sm center',

            click: function () {
                $.post("/api/game/" + value.id)

                    .done(function(data){
                        console.log(data);
                        location = "/game.html?gp=" + data.gpId;

                    })


            }
        })

        if(participation.length == 1){
            row.append($('<td class="center">' + "--" +'</td>'))
            row.append(joinButton);
        }else{
            var playButton = $('<td/>', {

               text: 'Play',
               id: 'play_game' + value.id,
               class: 'buttonCells btn btn-warning btn-sm center',

               click: function () {
                   window.location.replace(url);
                        }
          });
        }



            if(setButton){
                row.append(playButton);
            }else{
            row.append($("<td/>"));
            }

            tBody.append(row);
            tbl.append(tblHead);
                        tbl.append(tBody);

                        $('.battleSelect').append(tbl);

     })

 }

function createGame(){
    $.post("/api/game")

        .done(function(){
            console.log("New Battle Created!");
            location.reload();


        })
        .fail(function(){
        console.log("Create game failed")})
}





