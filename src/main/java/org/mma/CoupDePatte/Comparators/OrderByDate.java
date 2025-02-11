package org.mma.CoupDePatte.Comparators;

import org.mma.CoupDePatte.Models.DTO.MsgDTO;

import java.util.Comparator;

public class OrderByDate implements Comparator<MsgDTO> {
    public int compare(MsgDTO msg1, MsgDTO msg2){
        return msg1.date().compareTo(msg2.date());
    }
}
