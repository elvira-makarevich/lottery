package by.discord.lottery.service;

import by.discord.lottery.entity.Member;
import by.discord.lottery.repository.LotteryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static by.discord.lottery.util.ConstStringParameter.*;

@RequiredArgsConstructor
@Service
public class LotteryServiceImpl implements LotteryService {

    private static LocalTime startTime;
    private static LocalTime endTime;
    private final LotteryMemberRepository lotteryMemberRepository;
    private Boolean lotteryStatus = false;

    @Override
    public String startLottery() {
        if (lotteryStatus) {
            return STATUS_ALREADY_TRUE;
        } else {
            startTime = LocalTime.now();
            lotteryStatus = true;
            return STATUS_TRUE;
        }
    }

    @Override
    public String participate(Member member) {
        if (!lotteryStatus) {
            return STATUS_FALSE;
        }

        if (isAlreadyParticipating(member)) {
            return ALREADY_ADDED_TO_LIST;
        }
        lotteryMemberRepository.addLotteryMember(member);
        return member.getName() + ADDED_NEW_PARTICIPANT;
    }

    @Override
    public String getLotteryMembers() {
        if (!lotteryStatus) {
            return STATUS_FALSE;
        }
        List<Member> lotteryMembers = lotteryMemberRepository.getLotteryMembers();
        if (lotteryMembers.isEmpty()) {
            return EMPTY_PARTICIPANT_LIST;
        }
        return lotteryMembers.stream().map(Member::getName)
                .collect(Collectors.joining(DELIMETER, LOTTERY_MEMBERS, SUFFIX));
    }

    @Override
    public String endLottery() {
        if (lotteryStatus) {

            endTime = LocalTime.now();
            lotteryStatus = false;
            List<Member> memberList = lotteryMemberRepository.getLotteryMembers();

            if (memberList.isEmpty()) {
                return EMPTY_PARTICIPANT_LIST;
            } else {
                String winner = determineTheWinner(memberList);
                lotteryMemberRepository.clearListMembers();
                return LOTTERY_ENDED + getLotteryPeriod() + winner + WON_LOTTERY;
            }

        } else {
            return LOTTERY_NOT_STARTED;
        }
    }


    private boolean isAlreadyParticipating(Member member) {
        List<Member> lotteryMembers = lotteryMemberRepository.getLotteryMembers();
        for (Member m : lotteryMembers) {
            if (m.getId().equals(member.getId())) {
                return true;
            }
        }
        return false;
    }

    private String determineTheWinner(List<Member> memberList) {
        return memberList.get(new Random().nextInt(memberList.size())).getName();
    }

    private String getLotteryPeriod() {
        Duration duration = Duration.between(startTime, endTime);
        return LOTTERY_DURATION + duration.getSeconds() / 60 + MINUTES +
                duration.getSeconds() % 60 + SECONDS;
    }

}
