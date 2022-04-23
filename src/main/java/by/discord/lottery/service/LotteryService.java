package by.discord.lottery.service;

import by.discord.lottery.entity.Member;

public interface LotteryService {

    String startLottery();

    String endLottery();

    String getLotteryMembers();

    String participate(Member member);
}
