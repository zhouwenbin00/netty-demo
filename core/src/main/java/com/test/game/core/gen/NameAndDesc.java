package com.test.game.core.gen;

import java.util.Objects;

/** @Auther: zhouwenbin @Date: 2019/8/10 17:08 */
public class NameAndDesc {
    public final String name;
    public final String desc;

    public NameAndDesc(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()) {
            return false;
        } else if (this == o) {
            return true;
        } else if (!(o instanceof NameAndDesc)) {
            return false;
        } else {
            NameAndDesc that = (NameAndDesc) o;
            if (this.name != null) {
                if (this.name.equals(that.name)) {
                    return Objects.equals(this.desc, that.desc);
                }
            } else if (that.name == null) {
                return Objects.equals(this.desc, that.desc);
            }

            return false;
        }
    }

    public int hashCode() {
        int result = this.name != null ? this.name.hashCode() : 0;
        result = 31 * result + (this.desc != null ? this.desc.hashCode() : 0);
        return result;
    }
}
