<?xml version="1.0"?>
<!--
  ********************************************************
  * PKS - Planning with Knowledge and Sensing            *
  *                                                      *
  * Version 0.7alpha2                                    *
  *                                                      *
  * Copyright (c) 2002-2004, Ron Petrick                 *
  *                                                      *
  * This software is provided without warranty.          *
  * Please read the full LICENCE document.               *
  * Send questions to rpetrick@cs.utoronto.ca            *
  ********************************************************
  * Domain: game                                         * 
  ********************************************************
-->
<pks>
  <domain name="room">
    <symbols>
      <predicates>
        is_object/1, is_grabbed/1, is_free/1, is_touching/2, can_be_grabbed/1, is_obstacle/1
      </predicates>
      <functions>
        state/1
      </functions>
      <constants>
<!--constants-->hand, uncut, cut, broken, unlocked, paper, scissors, key, glassdoor, woodendoor,pliers,glassbox,knife
      </constants>
    </symbols>

    <actions>
<action name="grab">
 <params>?x</params>
<preconds>
          K(is_object(?x)) ^ K(is_free(?x)) ^ K(is_free(hand))
        </preconds>
<effects>
add(Kf, is_grabbed(?x));
del(Kf, is_free(?x));
del(Kf, is_free(hand));
        </effects>
</action>

<action name="drop">
 <params>?x</params>
<preconds>
          K(is_object(?x)) ^ K(is_grabbed(?x))
        </preconds>
<effects>
add(Kf, is_free(?x));
add(Kf, is_free(hand));
del(Kf, is_grabbed(?x));
        </effects>
</action>

<action name="break">
 <params>?x, ?y</params>
<preconds>
          K(is_object(?x)) ^ K(is_grabbed(?x)) ^ K(is_object(?y)) ^ !K(?x = ?y)
        </preconds>
<effects>
<!--break-->K(scissors = ?x) ^ K(glassdoor = ?y) => del(Kw, is_obstacle(glassdoor))^ add(Kf, is_object(pliers)) ^ add(Kf, is_free(pliers)) ^ add(Kf, can_be_grabbed(pliers))^ add(Kf, state(glassdoor) = broken);K(scissors = ?x) ^ K(glassbox = ?y) => add(Kf, is_object(knife)) ^ add(Kf, is_free(knife)) ^ add(Kf, can_be_grabbed(knife)) ^ add(Kf, state(glassbox) = broken);

        </effects>
</action>

<action name="unlock">
 <params>?x, ?y</params>
<preconds>
          K(is_object(?x)) ^ K(is_grabbed(?x)) ^ K(is_object(?y)) ^ !K(?x = ?y)
        </preconds>
<effects>
<!--unlock--> K(pliers = ?x) ^ K(woodendoor = ?y) => del(Kw, is_obstacle(woodendoor)) ^ add(Kf, is_object(glassbox)) ^ add(Kf, is_free(glassbox)) ^ add(Kf, is_obstacle(glassbox)) ^ add(Kf, state(woodendoor) = unlocked);
        </effects>
</action>

<action name="cut">
 <params>?x, ?y</params>
<preconds>
          K(is_object(?x)) ^ K(is_grabbed(?x)) ^ K(is_object(?y)) ^ !K(?x = ?y)
        </preconds>
<effects>
<!--cut--> 
        </effects>
</action>

</actions>
</domain>
<problem name="cut-paper" domain="room">
    <init>
<!--is_object-->add(Kf, is_object(paper)); add(Kf, is_object(scissors)); add(Kf, is_object(key)); add(Kf, is_object(glassdoor)); add(Kf, is_object(woodendoor)); 
<!--can_be_grabbed-->add(Kf, can_be_grabbed(scissors)); add(Kf, can_be_grabbed(key)); 
<!--is_free-->add(Kf, is_free(scissors)); add(Kf, is_free(key)); 
<!--is_obstacle-->add(Kf, is_obstacle(glassdoor)); add(Kf, is_obstacle(woodendoor)); 
<!--state-->

add(Kf, is_free(hand));

    </init>
    <goal>
<!--goal--><!--goal-->K(state(glassbox)= broken)^ K(is_free(scissors))^ K(is_free(key))^ K(is_free(pliers))^ K(is_free(pliers))^ K(is_free(glassbox))^ K(is_free(knife))
    </goal>
<updates>

</updates>

  </problem>
</pks>
