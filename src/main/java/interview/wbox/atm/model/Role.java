package interview.wbox.atm.model;

import javax.persistence.*;

/**
 * Created by ioana on 11/29/2020.
 */
    @Entity
    @Table(name = "role", schema="public")
    public class Role {

        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        @Column(name = "id")
        private long id;
        @Enumerated(EnumType.STRING)
        @Column(name = "name", unique = true)
        private RoleType name;

        public Role(){}
        public Role(RoleType roleType) {
            this.name = roleType;
        }

        public RoleType getName() {
            return name;
        }

        public void setName(RoleType name) {
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
}
