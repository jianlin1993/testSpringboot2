package com.wxy.wjl.testspringboot2.proxy;

public class CglibProxyTest {
    public static void main(String[] args){
        PlayGames playGames = new PlayGames();
        CglibProxy cglibProxy = new CglibProxy();
        PlayGames playGamesCglibProxy = (PlayGames) cglibProxy.getInstance(playGames);
        playGamesCglibProxy.plays();
    }
}
