// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
   // throw new UnsupportedOperationException("TODO: Implement this method.");
    ArrayList<Event> eventList = new ArrayList<>(events);
    ArrayList<String> attendees = new ArrayList<>(request.getAttendees());
    ArrayList<TimeRange> unavailable = new ArrayList<>();
    ArrayList<TimeRange> available = new ArrayList<>();

    //Cases that don't need to go through loops
    if(attendees.isEmpty()){
        available.add(TimeRange.fromStartEnd(0, 24 * 60,false));
        return available;
    }

    if(request.getDuration() > 1440){
        return available;
    }

    //Finds unavailable times
    TimeRange unavailableTime = TimeRange.fromStartEnd(0,0,false);
    TimeRange smallest = TimeRange.fromStartEnd(0,1440,false);
    for(int i=0; i < attendees.size(); i++){
        String person = attendees.get(i);
        System.out.println(person);
        for(int j =0; j < eventList.size(); j++){
            Event currEvent = eventList.get(j);
            if(currEvent.getAttendees().contains(person)){
                unavailableTime = currEvent.getWhen();

                //Makes sure there's no duplicates
                if(!(unavailable.contains(unavailableTime))){
                    unavailable.add(unavailableTime);
                }
                
            }

            //Finds the smallest event in case of nested events
            if(unavailableTime.duration() < smallest.duration()){
            smallest = unavailableTime;
            System.out.println("Smallest: " +smallest);
            }
        }
    }
    //Beginning of day
    int start = 0;

    //Creates available times based off unavailable times
    TimeRange availableTime;
    for(int i =0; i < unavailable.size(); i++){
        TimeRange element = unavailable.get(i);
        availableTime = TimeRange.fromStartEnd(start,element.start(),false);
        if(availableTime.duration() >= request.getDuration()){
            available.add(availableTime);
        }
        //Sets new start to the end of an avail. time
        start = element.end();

    }

    //Finds the longest of nested events
    for(TimeRange t:unavailable){
        if(t.contains(smallest) && !(t.equals(smallest))){
            start = t.end();
        }
    }

    /*Gets available time from the end of the last event, to the end of
    the day */
    TimeRange lastAvailableTime = TimeRange.fromStartEnd(start,1440,false);
    if(lastAvailableTime.duration() != 0){
    available.add(lastAvailableTime);
    }

    return available;
}

}