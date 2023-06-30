const express = require('express');
const http = require('http');
const { Server } = require("socket.io");
const app = express();
const server = http.createServer(app);
const io = new Server(server);

var jugadores = [];
var enemigos = [];

function jugador(id,x,y,xBala,yBala) {
this.id = id;
this.x = x;
this.y = y;
this.xBala = xBala;
this.yBala = yBala;
}

function enemigo(x,y,xBala,yBala) {
this.x = x;
this.y = y;
this.xBala = xBala;
this.yBala = yBala;
}

io.on('connection', (socket) => {
  console.log('Jugador Conectado.');

  socket.emit('socketID', { id: socket.id });

  socket.emit('obtenerJugadores', jugadores);

  socket.broadcast.emit('nuevoJugador', { id: socket.id });

  socket.on('disconnect', () => {
  		console.log("Jugador Desconectado.");
  		socket.broadcast.emit('jugadorDesconectado', {id: socket.id});

  		for (var i = 0; i < jugadores.length; i++) {
  		if (jugadores[i].id == socket.id) {
  		jugadores.splice(i,1);
      }
  	}
  	});

  socket.on('actualizarPosiciones', (data) => {
      data.id = socket.id;

      socket.broadcast.emit('actualizarPosiciones', data);

      for (var i = 0; i < jugadores.length; i++) {
      if (jugadores[i].id == data.id) {
      jugadores[i].x = data.x;
      jugadores[i].y = data.y;
      jugadores[i].xBala = data.xBala;
      jugadores[i].yBala = data.yBala;
      }
      }
      });

  jugadores.push(new jugador(socket.id,0,0,0,0));
});

server.listen(3000, () => {
  console.log('Server corriendo...');
});