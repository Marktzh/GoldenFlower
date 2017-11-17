package com.acemurder.game.player;

import com.acemurder.game.Manager;
import com.acemurder.game.Player;
import com.acemurder.game.Poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Markplayer implements Player {


    public String getName() {
        return "小明";
    }

    public String getStuNum() {
        return "2017210467";
    }

    @Override

    public void onGameStart(Manager manager, int totalPlayer) {

        
    }


    /**
     * 下注
     *
     * @param time                   第几局
     * @param round                  每局游戏会有多轮，当前轮数，最多会有5轮
     * @param lastPerson             还没有弃牌的玩家数
     * @param moneyOnDesk            桌上的筹码数量
     * @param moneyYouNeedToPayLeast 你本次最小需要下注的数量，
     * @param pokers                 你的手牌，三张
     * @return 你的下注数量，小于这个最小下注数量或者大于最小下注数量的三倍，都会被当作弃牌处理。
     */

    @Override
    public int bet(int time, int round, int lastPerson, int moneyOnDesk, int moneyYouNeedToPayLeast, List<Poker> pokers) {
        Collections.sort(pokers);
        if (isSameColor(pokers))
            return 3*moneyYouNeedToPayLeast;/*(int) ((2 + (round / 10f)) * moneyYouNeedToPayLeast) < 3 * moneyOnDesk ? (int) ((2 + (round / 10f)) * moneyYouNeedToPayLeast) : 3 * moneyOnDesk - 1;*/
        if ((isSameColorStraight(pokers) || isSamePoint(pokers)))
            return 3*moneyYouNeedToPayLeast;/*(int) ((2 + (round / 10f)) * moneyYouNeedToPayLeast) < 3 * moneyOnDesk ? (int) ((2 + (round / 10f)) * moneyYouNeedToPayLeast) : 3 * moneyOnDesk - 1;*/
        if (isPair(pokers))
            return (int) (1.3 * moneyYouNeedToPayLeast) < 3 * moneyOnDesk ? (int) (1.3 * moneyYouNeedToPayLeast) : moneyYouNeedToPayLeast;

        if(bigPoint(pokers)){
            if(round<=5){
                return 1*moneyYouNeedToPayLeast;
            }
            else return 0;
        }

        if (isStraight(pokers))
            return (int) (1.7 * moneyYouNeedToPayLeast) < 3 * moneyOnDesk ? (int) (1.7 * moneyYouNeedToPayLeast) : moneyYouNeedToPayLeast;
        for (Poker p : pokers) {
            if (p.getPoint().getNum() >= 14)
                return moneyYouNeedToPayLeast;
        }
        return 0;
    }

    @Override
    public void onResult(int time, boolean isWin, List<Poker> pokers) {

    }


    private boolean isSameColor(List<Poker> pokers) {
        return pokers.get(0).getSuit() == pokers.get(1).getSuit() &&
                pokers.get(1).getSuit() == pokers.get(2).getSuit();
    }

    private boolean isPair(List<Poker> pokers) {
        return pokers.get(0).getPoint().getNum() == pokers.get(1).getPoint().getNum()
                || pokers.get(1).getPoint().getNum() == pokers.get(2).getPoint().getNum()
                || pokers.get(0).getPoint().getNum() == pokers.get(2).getPoint().getNum();
    }

    private boolean isStraight(List<Poker> pokers) {
        Collections.sort(pokers);
        return Math.abs(pokers.get(0).getPoint().getNum() - pokers.get(1).getPoint().getNum()) == 1
                && Math.abs(pokers.get(1).getPoint().getNum() - pokers.get(2).getPoint().getNum()) == 1;

    }

    private boolean isSameColorStraight(List<Poker> handCards) {
        return isSameColor(handCards) && isStraight(handCards);
    }

    private boolean isSamePoint(List<Poker> handCards) {
        return handCards.get(0).getPoint() == handCards.get(1).getPoint()
                && handCards.get(2).getPoint() == handCards.get(1).getPoint();
    }

    private boolean bigPoint(List<Poker> handCards) {
        return handCards.get(0).getPoint() == Poker.Point.A|| handCards.get(1).getPoint()== Poker.Point.A||
                 handCards.get(2).getPoint() == Poker.Point.A;
    }
   /* private boolean big(List<Poker> pokers) {
        return pokers.get(0).getSuit() == pokers.get(1).getSuit() &&
                pokers.get(1).getSuit() == pokers.get(2).getSuit();
    }*/
}


