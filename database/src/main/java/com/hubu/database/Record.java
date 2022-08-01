package com.hubu.database;

public class Record{
        private Long id;
        private String name;
        private int price;
        private String address;
        //分类
        private String type;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Record(Long id, String name, String address, String type, int p){
            this.price = p;
            this.id = id;
            this.name = name;
            this.type = type;
            this.address = address;
        }
    }