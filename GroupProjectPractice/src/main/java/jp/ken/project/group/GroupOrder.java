package jp.ken.project.group;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence(value = {Default.class, Group1.class,Group2.class,Group3.class,Group4.class,Group5.class})
public interface GroupOrder {

}
