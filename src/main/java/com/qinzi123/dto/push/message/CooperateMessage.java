package com.qinzi123.dto.push.message;

import com.qinzi123.dto.push.Keyword;

/**
 * @title: CooperateMessage
 * @package: com.qinzi123.dto.push.message
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class CooperateMessage {
    private Keyword thing1;
    private Keyword thing2;
    private Keyword thing4;
    private Keyword time3;

    public Keyword getThing1() {
        return thing1;
    }

    public void setThing1(Keyword thing1) {
        this.thing1 = thing1;
    }

    public Keyword getThing2() {
        return thing2;
    }

    public void setThing2(Keyword thing2) {
        this.thing2 = thing2;
    }

    public Keyword getThing4() {
        return thing4;
    }

    public void setThing4(Keyword thing4) {
        this.thing4 = thing4;
    }

    public Keyword getTime3() {
        return time3;
    }

    public void setTime3(Keyword time3) {
        this.time3 = time3;
    }
}
