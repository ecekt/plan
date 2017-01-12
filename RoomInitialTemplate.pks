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
<!--constants-->
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
<!--break-->

        </effects>
</action>

<action name="unlock">
 <params>?x, ?y</params>
<preconds>
          K(is_object(?x)) ^ K(is_grabbed(?x)) ^ K(is_object(?y)) ^ !K(?x = ?y)
        </preconds>
<effects>
<!--unlock-->
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
<!--is_object-->
<!--can_be_grabbed-->
<!--is_free-->
<!--is_obstacle-->
<!--state-->

add(Kf, is_free(hand));

    </init>
    <goal>
    <!--goal-->
    </goal>
<updates>

</updates>

  </problem>
</pks>
