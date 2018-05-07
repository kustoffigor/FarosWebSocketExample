package Faros.ReactiveWebsocketExample.WebSocket;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.TopicProcessor;
import reactor.core.publisher.UnicastProcessor;

import java.awt.*;
import java.time.Duration;
import java.util.HashMap;

@Component
public class WebSocketHandlers implements WebSocketHandler
{
    private Flux<String> result = Flux.just("ForNow");
    private TopicProcessor<String> topicProcessor = TopicProcessor.create();


    public WebSocketHandlers()
    {
        result.subscribe(topicProcessor);
    }
    public Flux<String> getResult()
    {
        topicProcessor.subscribe(System.out::println);
        return Flux.from(topicProcessor);
    }

    public Flux<String> reverseString(String userInput)
    {
        result = Flux.just(new StringBuilder(userInput).reverse().toString());
        return result;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {

        session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .map(this::reverseString)
                .subscribe(System.out::println);
        return session.send(getResult().map(session::textMessage));
    }

}
