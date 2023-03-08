package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.response.APIResponse;
import com.shyam.gujarat_police.services.InternalPurposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/internal_purpose")
public class InternalPurpose {

    @Autowired
    private InternalPurposeService internalPurposeService;

    @DeleteMapping("/assign-police/reset-event-police/{eventId}")
    public APIResponse deleteAssignmentsInEntireEvent(@PathVariable("eventId") Long eventId) {
        return internalPurposeService.deleteAssignmentsInEntireEvent(eventId);
    }

    @GetMapping("/parth")
    public APIResponse kuchtohDedeReBaba(){

        LocalDateTime date1 = LocalDateTime.now();
        System.out.println(date1.plus(Period.ofDays(-1)));
//        Scam.bakri();
//        TreeSet set = new TreeSet();
//        set.add("a");
//        set.add("b");
//        set.add(9);
//        Iterator a = set.iterator();
//        while(a.hasNext()) {
//            System.out.println(a.next());
//        }

//        HashSet abc = new LinkedHashSet();
//        abc.add("Jack");
//        abc.add("Thomas");
//        abc.add("Aohn");
//        System.out.println(abc);

//        List<String> list1 = new ArrayList<String>();
//        list1.add("1");
//        list1.add("2");
//        list1.add(1,"3");
//        List<String> list2 = new LinkedList<>(list1);
//        list1.addAll(list2);
//        list2 = list1.subList(2,6);
//        list2.clear();
//        System.out.println(list1 + " ");

//        List<String> list1 = new ArrayList<String>();
//        list1.add("aAaA");
//        list1.add("AaA");
//        list1.add("aAa");
//        Collections.sort(list1);
//        for (String string : list1) {
//            System.out.println(string + " ");
//        }
//
//        int counter = 0;
//        try {
//            counter += 10;
//            assert counter - 10 < 10 : counter += 10;
//        } catch(Error e) {
//            System.out.println("0");
//            counter -= 10;
//        }
//        System.out.println(counter);
//        try {
//            System.out.println("hii");
//            return APIResponse.ok("sdkvnj");
//        } finally {
//            System.out.println("hii");
////            return APIResponse.ok("sdkvnjasKJFBKAs");
//        }
//        System.out.println("hii");
////        return APIResponse.ok("PArth variable");


//        for (int i = 0; i < 6; ++i) {
//            try {
//                if(i%3 == 0) throw new Exception("sldjk f");
//                try {
//                    if(i%3 == 1) throw new Exception("sldjk f kasjdnf");
//                    System.out.println("here");
//
//                } catch (Exception en) {
//                    i *= 2;
//                } finally {
//                    ++i;
//                }
//            } catch (Exception e) {
//                i+= 3;
//            } finally {
//                ++i;
//            }
//        }

        int[] myArry = {11,22,33,44,55,66,77,88,99,109};
        int position = 3;
        int value = 7;
        for (int i = myArry.length - 1; i > position; i--) {
            myArry[i] = myArry[i-1];

        }
        myArry[position] = value;
        System.out.println("New array: " + Arrays.toString(myArry));




        return APIResponse.ok("PArth variable");
    }
}

interface Training{
    public static void bakri(){
        System.out.println("bakri");
    }
}

class Scam implements Training {
    public static void bakri(){
        System.out.println("bakri in the house");

    }
}


