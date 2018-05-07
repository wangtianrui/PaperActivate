package wuxiang.miku.scorpio.paperactivate.bean;

import java.util.List;

public class OcrGenWords {

    /**
     * log_id : 583099754038866844
     * direction : 0
     * words_result_num : 1
     * words_result : [{"vertexes_location":[{"y":42,"x":16},{"y":42,"x":138},{"y":75,"x":138},{"y":75,"x":16}],"chars":[{"char":"不","location":{"width":25,"top":44,"height":31,"left":24}},{"char":"要","location":{"width":25,"top":44,"height":31,"left":52}},{"char":"使","location":{"width":26,"top":44,"height":31,"left":81}},{"char":"用","location":{"width":18,"top":44,"height":31,"left":121}}],"min_finegrained_vertexes_location":[{"y":41,"x":16},{"y":42,"x":137},{"y":74,"x":137},{"y":74,"x":16}],"finegrained_vertexes_location":[{"y":42,"x":16},{"y":42,"x":46},{"y":42,"x":76},{"y":42,"x":106},{"y":42,"x":136},{"y":42,"x":138},{"y":57,"x":138},{"y":72,"x":138},{"y":75,"x":138},{"y":75,"x":112},{"y":75,"x":82},{"y":75,"x":52},{"y":75,"x":22},{"y":75,"x":16},{"y":60,"x":16},{"y":45,"x":16}],"location":{"width":123,"top":42,"height":34,"left":16},"words":"不要使用"}]
     */

    private long log_id;
    private int direction;
    private int words_result_num;
    private List<WordsResultBean> words_result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }

    public List<WordsResultBean> getWords_result() {
        return words_result;
    }

    public void setWords_result(List<WordsResultBean> words_result) {
        this.words_result = words_result;
    }

    public static class WordsResultBean {
        /**
         * vertexes_location : [{"y":42,"x":16},{"y":42,"x":138},{"y":75,"x":138},{"y":75,"x":16}]
         * chars : [{"char":"不","location":{"width":25,"top":44,"height":31,"left":24}},{"char":"要","location":{"width":25,"top":44,"height":31,"left":52}},{"char":"使","location":{"width":26,"top":44,"height":31,"left":81}},{"char":"用","location":{"width":18,"top":44,"height":31,"left":121}}]
         * min_finegrained_vertexes_location : [{"y":41,"x":16},{"y":42,"x":137},{"y":74,"x":137},{"y":74,"x":16}]
         * finegrained_vertexes_location : [{"y":42,"x":16},{"y":42,"x":46},{"y":42,"x":76},{"y":42,"x":106},{"y":42,"x":136},{"y":42,"x":138},{"y":57,"x":138},{"y":72,"x":138},{"y":75,"x":138},{"y":75,"x":112},{"y":75,"x":82},{"y":75,"x":52},{"y":75,"x":22},{"y":75,"x":16},{"y":60,"x":16},{"y":45,"x":16}]
         * location : {"width":123,"top":42,"height":34,"left":16}
         * words : 不要使用
         */

        private LocationBean location;
        private String words;
        private List<VertexesLocationBean> vertexes_location;
        private List<CharsBean> chars;
        private List<MinFinegrainedVertexesLocationBean> min_finegrained_vertexes_location;
        private List<FinegrainedVertexesLocationBean> finegrained_vertexes_location;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }

        public List<VertexesLocationBean> getVertexes_location() {
            return vertexes_location;
        }

        public void setVertexes_location(List<VertexesLocationBean> vertexes_location) {
            this.vertexes_location = vertexes_location;
        }

        public List<CharsBean> getChars() {
            return chars;
        }

        public void setChars(List<CharsBean> chars) {
            this.chars = chars;
        }

        public List<MinFinegrainedVertexesLocationBean> getMin_finegrained_vertexes_location() {
            return min_finegrained_vertexes_location;
        }

        public void setMin_finegrained_vertexes_location(List<MinFinegrainedVertexesLocationBean> min_finegrained_vertexes_location) {
            this.min_finegrained_vertexes_location = min_finegrained_vertexes_location;
        }

        public List<FinegrainedVertexesLocationBean> getFinegrained_vertexes_location() {
            return finegrained_vertexes_location;
        }

        public void setFinegrained_vertexes_location(List<FinegrainedVertexesLocationBean> finegrained_vertexes_location) {
            this.finegrained_vertexes_location = finegrained_vertexes_location;
        }

        public static class LocationBean {
            /**
             * width : 123
             * top : 42
             * height : 34
             * left : 16
             */

            private int width;
            private int top;
            private int height;
            private int left;

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }
        }

        public static class VertexesLocationBean {
            /**
             * y : 42
             * x : 16
             */

            private int y;
            private int x;

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }
        }

        public static class CharsBean {
            /**
             * char : 不
             * location : {"width":25,"top":44,"height":31,"left":24}
             */

            private String charX;
            private LocationBeanX location;

            public String getCharX() {
                return charX;
            }

            public void setCharX(String charX) {
                this.charX = charX;
            }

            public LocationBeanX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanX location) {
                this.location = location;
            }

            public static class LocationBeanX {
                /**
                 * width : 25
                 * top : 44
                 * height : 31
                 * left : 24
                 */

                private int width;
                private int top;
                private int height;
                private int left;

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }
            }
        }

        public static class MinFinegrainedVertexesLocationBean {
            /**
             * y : 41
             * x : 16
             */

            private int y;
            private int x;

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }
        }

        public static class FinegrainedVertexesLocationBean {
            /**
             * y : 42
             * x : 16
             */

            private int y;
            private int x;

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }
        }
    }
}
