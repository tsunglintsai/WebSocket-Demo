//
//  ViewController.m
//  WebSocketClient
//
//  Created by Daniela on 4/26/13.
//  Copyright (c) 2013 Pyrogusto. All rights reserved.
//

#import "ViewController.h"
#import "SRWebSocket.h"

//#define webSocketServerURL @"ws://localhost:8080/WebsocketServer/websocket/echoStream"
#define webSocketServerURL @"ws://localhost:8080/WebSocketServerJ2EE7/websocket/"

@interface ViewController ()<SRWebSocketDelegate>
@property (strong,nonatomic) SRWebSocket *webSocket;
@end

@implementation ViewController

-(void)viewDidAppear:(BOOL)animated{
    [self reconnect];
}

- (SRWebSocket*) webSocket{
    if(!_webSocket){
        _webSocket = [[SRWebSocket alloc] initWithURLRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:webSocketServerURL]]];
        _webSocket.delegate = self;

    }
    return _webSocket;
}

- (void)reconnect{
    self.webSocket.delegate = nil;
    [self.webSocket close];
    self.webSocket = nil;
    [self.webSocket open];
}


#pragma mark - SRWebSocketDelegate

- (void)webSocketDidOpen:(SRWebSocket *)webSocket{
    NSLog(@"Websocket Connected");
    self.title = @"Connected!";
    
    NSString *message = @"test message";
    [self.webSocket send:message];
}

- (void)webSocket:(SRWebSocket *)webSocket didFailWithError:(NSError *)error{
    NSLog(@":( Websocket Failed With Error %@", error);
    
    self.title = @"Connection Failed! (see logs)";
    _webSocket = nil;
}

- (void)webSocket:(SRWebSocket *)webSocket didReceiveMessage:(id)message{
    NSLog(@"Received \"%@\"", message);
}

- (void)webSocket:(SRWebSocket *)webSocket didCloseWithCode:(NSInteger)code reason:(NSString *)reason wasClean:(BOOL)wasClean{
    NSLog(@"WebSocket closed");
    self.title = @"Connection Closed! (see logs)";
    _webSocket = nil;
}

@end
