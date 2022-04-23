package by.discord.lottery.repository;

import by.discord.lottery.entity.Member;

import java.util.List;

public interface LotteryMemberRepository {
    List<Member> getLotteryMembers();
    void addLotteryMember(Member member);
    void clearListMembers();
}
