package com.example.mednow;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

    @Entity(tableName = "ai_guidelines")
    public class AiGuideline {
        @PrimaryKey(autoGenerate = true)
        public int id;

        public String emergencyType;
        public String query;
        public String response;
        public long timestamp;
        public boolean isAiGenerated;

        public AiGuideline(String emergencyType, String query, String response, long timestamp, boolean isAiGenerated) {
            this.emergencyType = emergencyType;
            this.query = query;
            this.response = response;
            this.timestamp = timestamp;
            this.isAiGenerated = isAiGenerated;
        }
    }

