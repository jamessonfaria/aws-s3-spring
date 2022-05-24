package br.com.jamesson.aws_s3.service;

import br.com.jamesson.aws_s3.config.AmazonConfig;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;

import java.util.List;

public class BucketServices {
    private static final String ID = "";

    public static Bucket getBucket(String bucketName){
        final AmazonS3 s3 = AmazonConfig.s3WithCredentials();
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket bucket : buckets) {
            if(bucket.getName().equals(bucketName))
                return bucket;
        }

        System.err.println("Bucket not found.");
        return null;
    }

    public static List<Bucket> listOfBuckets(){
        final AmazonS3 s3 = AmazonConfig.s3WithCredentials();
        List<Bucket> buckets = s3.listBuckets();
        return buckets;
        //System.out.println("List of buckets on your account.");
        //for (Bucket bucket : buckets)
        //    System.out.format("-> %s", bucket.getName());
    }

    public static Bucket createBucket(String bucketName){
        final AmazonS3 s3 = AmazonConfig.s3WithCredentials();
        Bucket newBucket = null;
        boolean doesExist = s3.doesBucketExistV2(bucketName);
        if(doesExist){
            System.out.println("The bucket already exists!");
            try{
                newBucket = getBucket(bucketName);
            }catch (AmazonS3Exception e){
                System.err.println("You are not the owner of this bucket! " +
                        "The bucket name is not available. \nMessage error: " + e.getMessage());
                System.exit(1);
            }
        }

        try {
            newBucket = s3.createBucket(bucketName);
        }catch (AmazonS3Exception e){
            System.out.println("Error during the bucket creation!");
            System.err.println("Message error:\n" + e.getMessage());
        }

        return newBucket;
    }

}
