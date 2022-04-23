package by.discord.lottery.repository;

import by.discord.lottery.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LotteryMemberRepositoryImpl implements LotteryMemberRepository {

    private final List<Member> lotteryMembers = new ArrayList<>();

    @Override
    public List<Member> getLotteryMembers() {
        return lotteryMembers;
    }

    @Override
    public void addLotteryMember(Member member) {
        lotteryMembers.add(member);
    }

    @Override
    public void clearListMembers() {
        lotteryMembers.clear();
    }
}
