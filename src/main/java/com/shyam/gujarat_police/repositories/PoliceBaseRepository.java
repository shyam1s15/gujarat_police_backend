package com.shyam.gujarat_police.repositories;

import com.shyam.gujarat_police.entities.Police;

public interface PoliceBaseRepository {
    boolean isPoliceExists(Police station);
}
